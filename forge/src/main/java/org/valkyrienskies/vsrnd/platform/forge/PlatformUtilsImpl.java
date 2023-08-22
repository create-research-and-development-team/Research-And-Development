package org.valkyrienskies.vsrnd.platform.forge;

import com.google.common.collect.Multimap;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkHooks;
import org.valkyrienskies.vsrnd.util.fluid.VSCFluidTankBehaviour;

public class PlatformUtilsImpl {
    public static double getReachDistance(Player player) {
        return player.getReachDistance();
    }

    public static Packet<?> createExtraDataSpawnPacket(Entity entity) {
        return NetworkHooks.getEntitySpawningPacket(entity);
    }

    public static void setAboveGroundTicks(ServerPlayer player, int ticks) {
        // todo
    }

//    public static InteractionResultHolder<ItemStack> tryInsert(BlockState state, Level world, BlockPos pos,
//                                                               ItemStack stack, boolean doNotConsume, boolean forceOverflow, boolean simulate) {
//        if (!state.hasBlockEntity())
//            return InteractionResultHolder.fail(ItemStack.EMPTY);
//
//        BlockEntity te = world.getBlockEntity(pos);
//        if (!(te instanceof BalloonerBlockEntity))
//            return InteractionResultHolder.fail(ItemStack.EMPTY);
//        BalloonerBlockEntity burnerTE = (BalloonerBlockEntity) te;
//
//        if (burnerTE.isCreativeFuel(stack)) {
//            if (!simulate)
//                burnerTE.applyCreativeFuel();
//            return InteractionResultHolder.success(ItemStack.EMPTY);
//        }
//        if (!burnerTE.tryUpdateFuel(stack, forceOverflow, simulate))
//            return InteractionResultHolder.fail(ItemStack.EMPTY);
//
//        if (!doNotConsume) {
//            ItemStack container = stack.hasContainerItem() ? stack.getContainerItem() : ItemStack.EMPTY;
//            if (!world.isClientSide) {
//                stack.shrink(1);
//            }
//            return InteractionResultHolder.success(container);
//        }
//        return InteractionResultHolder.success(ItemStack.EMPTY);
//    }

//    public static boolean tryUpdateFuel(ItemStack itemStack, boolean forceOverflow, boolean simulate, BalloonerBlockEntity blockEntity) {
//        if (blockEntity.isCreative())
//            return false;
//
//        FuelType newFuel = FuelType.NONE;
//        int newBurnTime;
//
//        if (AllTags.AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.matches(itemStack)) {
//            newBurnTime = 3200;
//            newFuel = FuelType.SPECIAL;
//        } else {
//            newBurnTime = ForgeHooks.getBurnTime(itemStack, null);
//            if (newBurnTime > 0) {
//                newFuel = FuelType.NORMAL;
//            } else if (AllTags.AllItemTags.BLAZE_BURNER_FUEL_REGULAR.matches(itemStack)) {
//                newBurnTime = 1600; // Same as coal
//                newFuel = FuelType.NORMAL;
//            }
//        }
//
//        if (newFuel == FuelType.NONE)
//            return false;
//        if (newFuel.ordinal() < blockEntity.activeFuel.ordinal())
//            return false;
//
//        if (newFuel == blockEntity.activeFuel) {
//            if (blockEntity.remainingBurnTime <= INSERTION_THRESHOLD) {
//                newBurnTime += blockEntity.remainingBurnTime;
//            } else if (forceOverflow && newFuel == FuelType.NORMAL) {
//                if (blockEntity.remainingBurnTime < MAX_HEAT_CAPACITY) {
//                    newBurnTime = Math.min(blockEntity.remainingBurnTime + newBurnTime, MAX_HEAT_CAPACITY);
//                } else {
//                    newBurnTime = blockEntity.remainingBurnTime;
//                }
//            } else {
//                return false;
//            }
//        }
//
//        if (simulate)
//            return true;
//
//        blockEntity.activeFuel = newFuel;
//        blockEntity.remainingBurnTime = newBurnTime;
//
//        if (blockEntity.getLevel().isClientSide) {
//            blockEntity.spawnParticleBurst(blockEntity.activeFuel == FuelType.SPECIAL, blockEntity.activeFuel == FuelType.HYPER);
//            return true;
//        }
//
//        EngineHeatLevel prev = blockEntity.getHeatLevelFromBlock();
//        blockEntity.playSound();
//        blockEntity.updateBlockState();
//
//        if (prev != blockEntity.getHeatLevelFromBlock())
//            blockEntity.getLevel().playSound(null, blockEntity.getWorldPosition(), SoundEvents.BLAZE_AMBIENT, SoundSource.BLOCKS,
//                    .125f + blockEntity.getLevel().random.nextFloat() * .125f, 1.15f - blockEntity.getLevel().random.nextFloat() * .25f);
//
//        return true;
//    }
    public static int maxBalloonRange() {
        return 0;
    }


//    public static Class<?> getCombustionEngineTileEntityClass() {
//        return ForgeCombustionEngineBlockEntity.class;
//    }
//
//    public static BlockEntityType<? extends CombustionEngineBlockEntity> getCombustionEngineTileEntityType() {throw new AssertionError();}


    public static VSCFluidTankBehaviour cwFluidTank(BehaviourType<VSCFluidTankBehaviour> type, SmartBlockEntity te, int tanks, long tankCapacity, boolean enforceVariety) {
        return new ForgeVSCFluidTankBehaviour(type, te, tanks, tankCapacity, enforceVariety);
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static Attribute getReachModifier() {
        return ForgeMod.REACH_DISTANCE.get();
    }
}