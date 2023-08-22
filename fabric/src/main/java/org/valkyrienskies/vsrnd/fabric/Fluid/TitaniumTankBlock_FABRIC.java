package org.valkyrienskies.vsrnd.fabric.Fluid;


import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.foundation.fluid.FluidHelper.FluidExchange;
import io.github.fabricators_of_create.porting_lib.block.CustomSoundTypeBlock;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlock;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlockEntity;
import org.valkyrienskies.vsrnd.fabric.VSCreateFabricBlockEntities;

// uhhh
public class TitaniumTankBlock_FABRIC extends TitaniumTankBlock implements CustomSoundTypeBlock {


	public static final SoundType SILENCED_METAL =
			new SoundType(0.1F, 1.5F, SoundEvents.METAL_BREAK, SoundEvents.METAL_STEP,
						  SoundEvents.METAL_PLACE, SoundEvents.METAL_HIT, SoundEvents.METAL_FALL);

	protected TitaniumTankBlock_FABRIC(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	public static TitaniumTankBlock_FABRIC regular(Properties p_i48440_1_) {
		return new TitaniumTankBlock_FABRIC(p_i48440_1_);
	}

	public static boolean isTank(BlockState state) {
		return state.getBlock() instanceof TitaniumTankBlock_FABRIC;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
								 BlockHitResult ray) {
		ItemStack heldItem = player.getItemInHand(hand);
		boolean onClient = world.isClientSide;

        if (heldItem.isEmpty()) {
            return InteractionResult.PASS;
        }

		FluidExchange exchange = null;
		TitaniumTankBlockEntity_FABRIC be = ConnectivityHandler.partAt(getBlockEntityType(), world, pos);
        if (be == null) {
            return InteractionResult.FAIL;
        }

		Direction direction = ray.getDirection();
		Storage<FluidVariant> fluidTank = be.getFluidStorage(direction);
        if (fluidTank == null) {
            return InteractionResult.PASS;
        }

		FluidStack prevFluidInTank = TransferUtil.firstCopyOrEmpty(fluidTank);

        if (FluidHelper.tryEmptyItemIntoBE(world, player, hand, heldItem, be, direction)) {
            exchange = FluidExchange.ITEM_TO_TANK;
        } else if (FluidHelper.tryFillItemFromBE(world, player, hand, heldItem, be, direction)) {
            exchange = FluidExchange.TANK_TO_ITEM;
        }

		if (exchange == null) {
            if (GenericItemEmptying.canItemBeEmptied(world, heldItem)
                || GenericItemFilling.canItemBeFilled(world, heldItem)) {
                return InteractionResult.SUCCESS;
            }
			return InteractionResult.PASS;
		}

		SoundEvent soundevent = null;
		BlockState fluidState = null;
		FluidStack fluidInTank = TransferUtil.firstOrEmpty(fluidTank);

		if (exchange == FluidExchange.ITEM_TO_TANK) {


			Fluid fluid = fluidInTank.getFluid();
			fluidState = fluid.defaultFluidState()
							  .createLegacyBlock();
			soundevent = FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid));
		}

		if (exchange == FluidExchange.TANK_TO_ITEM) {

			Fluid fluid = prevFluidInTank.getFluid();
			fluidState = fluid.defaultFluidState()
							  .createLegacyBlock();
			soundevent = FluidVariantAttributes.getFillSound(FluidVariant.of(fluid));
		}

		if (soundevent != null && !onClient) {
			float pitch = Mth
					.clamp(1 - (1f * fluidInTank.getAmount() /
								(TitaniumTankBlockEntity_FABRIC.getCapacityMultiplier() * 16)), 0, 1);
			pitch /= 1.5f;
			pitch += .5f;
			pitch += (world.random.nextFloat() - .5f) / 4f;
			world.playSound(null, pos, soundevent, SoundSource.BLOCKS, .5f, pitch);
		}

		if (!fluidInTank.isFluidEqual(prevFluidInTank)) {
			if (be instanceof TitaniumTankBlockEntity_FABRIC) {
				TitaniumTankBlockEntity_FABRIC controllerBE = ((TitaniumTankBlockEntity_FABRIC) be).getControllerBE();
				if (controllerBE != null) {
					if (fluidState != null && onClient) {
						BlockParticleOption blockParticleData =
								new BlockParticleOption(ParticleTypes.BLOCK, fluidState);
						float level = (float) fluidInTank.getAmount() / TransferUtil.firstCapacity(fluidTank);

						boolean reversed = FluidVariantAttributes.isLighterThanAir(fluidInTank.getType());
                        if (reversed) {
                            level = 1 - level;
                        }

						Vec3 vec = ray.getLocation();
						vec = new Vec3(vec.x,
									   controllerBE.getBlockPos()
												   .getY() + level * (controllerBE.getHeight() - .5f) + .25f,
									   vec.z);
						Vec3 motion = player.position()
											.subtract(vec)
											.scale(1 / 20f);
						vec = vec.add(motion);
						world.addParticle(blockParticleData, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
						return InteractionResult.SUCCESS;
					}

					controllerBE.sendDataImmediately();
					controllerBE.setChanged();
				}
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public BlockEntityType<? extends TitaniumTankBlockEntity> getBlockEntityType() {
		return VSCreateFabricBlockEntities.TITANIUM_TANK.get();
	}

	@Override
	public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
		SoundType soundType = getSoundType(state);
		if (entity != null && entity.getExtraCustomData()
									.contains("SilenceTankSound"))
			return SILENCED_METAL;
		return soundType;
	}


}
