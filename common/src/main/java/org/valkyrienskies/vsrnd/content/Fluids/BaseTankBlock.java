package org.valkyrienskies.vsrnd.content.Fluids;

import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class BaseTankBlock extends Block {


    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", BaseTankBlock.Shape.class);
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
    static final VoxelShape CAMPFIRE_SMOKE_CLIP = Block.box(0, 4, 0, 16, 16, 16);

    protected BaseTankBlock(Properties p_i48440_1_) {
        super(setLightFunction(p_i48440_1_));
        registerDefaultState(defaultBlockState().setValue(TOP, true)
                .setValue(BOTTOM, true)
                .setValue(SHAPE, Shape.WINDOW)
                .setValue(LIGHT_LEVEL, 0));
    }


    private static Properties setLightFunction(Properties properties) {
        return properties.lightLevel(state -> state.getValue(LIGHT_LEVEL));
    }

    public static boolean isTank(BlockState state) {
        return state.getBlock() instanceof BaseTankBlock;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

    }

    // Handled via LIGHT_LEVEL state property
//	@Override
//	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
//		FluidTankBlockEntity tankAt = ConnectivityHandler.partAt(getBlockEntityType(), world, pos);
//		if (tankAt == null)
//			return 0;
//		FluidTankBlockEntity controllerBE = tankAt.getControllerBE();
//		if (controllerBE == null || !controllerBE.window)
//			return 0;
//		return tankAt.luminosity;
//	}

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(TOP, BOTTOM, SHAPE, LIGHT_LEVEL);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos,
                                        CollisionContext pContext) {
        if (pContext == CollisionContext.empty())
            return CAMPFIRE_SMOKE_CLIP;
        return pState.getShape(pLevel, pPos);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.block();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
//        if (pDirection == Direction.DOWN && pNeighborState.getBlock() != this)
//             withBlockEntityDo(pLevel, pCurrentPos, TitaniumTankBlockEntity);
        return pState;
    }

    //@Override
    //public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
    //                             BlockHitResult ray) {
    //    ItemStack heldItem = player.getItemInHand(hand);
    //    boolean onClient = world.isClientSide;
//
//        if (heldItem.isEmpty())
//            return InteractionResult.PASS;
//
//
//        FluidExchange exchange = null;
//        TitaniumTankBlockEntity be = ConnectivityHandler.partAt(getBlockEntityType(), world, pos);
//        if (be == null)
//            return InteractionResult.FAIL;
//
//        Direction direction = ray.getDirection();
//        Storage<FluidVariant> fluidTank = be.getFluidStorage(direction);
//        if (fluidTank == null)
//            return InteractionResult.PASS;
//
//        FluidStack prevFluidInTank = TransferUtil.firstCopyOrEmpty(fluidTank);
//
//        if (FluidHelper.tryEmptyItemIntoBE(world, player, hand, heldItem, be, direction))
//            exchange = FluidExchange.ITEM_TO_TANK;
//        else if (FluidHelper.tryFillItemFromBE(world, player, hand, heldItem, be, direction))
//            exchange = FluidExchange.TANK_TO_ITEM;
//
//        if (exchange == null) {
//            if (GenericItemEmptying.canItemBeEmptied(world, heldItem)
//                    || GenericItemFilling.canItemBeFilled(world, heldItem))
//                return InteractionResult.SUCCESS;
//            return InteractionResult.PASS;
//        }
//
//        SoundEvent soundevent = null;
//        BlockState fluidState = null;
//        FluidStack fluidInTank = TransferUtil.firstOrEmpty(fluidTank);
//
//        if (exchange == FluidExchange.ITEM_TO_TANK) {
//
//
//            Fluid fluid = fluidInTank.getFluid();
//            fluidState = fluid.defaultFluidState()
//                    .createLegacyBlock();
//            soundevent = FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid));
//        }
//
//        if (exchange == FluidExchange.TANK_TO_ITEM) {
//
//
//            Fluid fluid = prevFluidInTank.getFluid();
//            fluidState = fluid.defaultFluidState()
//                    .createLegacyBlock();
//            soundevent = FluidVariantAttributes.getFillSound(FluidVariant.of(fluid));
//        }
//
//        if (soundevent != null && !onClient) {
//            float pitch = Mth
//                    .clamp(1 - (1f * fluidInTank.getAmount() / (TitaniumTankBlockEntity.getCapacityMultiplier() * 16)), 0, 1);
//            pitch /= 1.5f;
//            pitch += .5f;
//            pitch += (world.random.nextFloat() - .5f) / 4f;
//            world.playSound(null, pos, soundevent, SoundSource.BLOCKS, .5f, pitch);
//        }
//
//        if (!fluidInTank.isFluidEqual(prevFluidInTank)) {
//            if (be instanceof TitaniumTankBlockEntity) {
//                TitaniumTankBlockEntity controllerBE = ((TitaniumTankBlockEntity) be).getControllerBE();
//                if (controllerBE != null) {
//                    if (fluidState != null && onClient) {
//                        BlockParticleOption blockParticleData =
//                                new BlockParticleOption(ParticleTypes.BLOCK, fluidState);
//                        float level = (float) fluidInTank.getAmount() / TransferUtil.firstCapacity(fluidTank);
//
//                        boolean reversed = FluidVariantAttributes.isLighterThanAir(fluidInTank.getType());
//                        if (reversed)
//                            level = 1 - level;
//
//                        Vec3 vec = ray.getLocation();
//                        vec = new Vec3(vec.x, controllerBE.getBlockPos()
//                                .getY() + level * (controllerBE.height - .5f) + .25f, vec.z);
//                        Vec3 motion = player.position()
//                                .subtract(vec)
//                                .scale(1 / 20f);
//                        vec = vec.add(motion);
//                        world.addParticle(blockParticleData, vec.x, vec.y, vec.z, motion.x, motion.y, motion.z);
//                        return InteractionResult.SUCCESS;
//                    }
//
//                    controllerBE.sendDataImmediately();
//                    controllerBE.setChanged();
//                }
//            }
//        }
//
//        return InteractionResult.SUCCESS;
//    }


    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE)
            return state;
        boolean x = mirror == Mirror.FRONT_BACK;
        switch (state.getValue(SHAPE)) {
            case WINDOW_NE:
                return state.setValue(SHAPE, x ? Shape.WINDOW_NW : Shape.WINDOW_SE);
            case WINDOW_NW:
                return state.setValue(SHAPE, x ? Shape.WINDOW_NE : Shape.WINDOW_SW);
            case WINDOW_SE:
                return state.setValue(SHAPE, x ? Shape.WINDOW_SW : Shape.WINDOW_NE);
            case WINDOW_SW:
                return state.setValue(SHAPE, x ? Shape.WINDOW_SE : Shape.WINDOW_NW);
            default:
                return state;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        for (int i = 0; i < rotation.ordinal(); i++)
            state = rotateOnce(state);
        return state;
    }

    private BlockState rotateOnce(BlockState state) {
        switch (state.getValue(SHAPE)) {
            case WINDOW_NE:
                return state.setValue(SHAPE, Shape.WINDOW_SE);
            case WINDOW_NW:
                return state.setValue(SHAPE, Shape.WINDOW_NE);
            case WINDOW_SE:
                return state.setValue(SHAPE, Shape.WINDOW_SW);
            case WINDOW_SW:
                return state.setValue(SHAPE, Shape.WINDOW_NW);
            default:
                return state;
        }
    }

    //@Override

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    // Tanks are less noisy when placed in batch
//    public static final SoundType SILENCED_METAL =
//            new SoundType(0.1F, 1.5F, SoundEvents.METAL_BREAK, SoundEvents.METAL_STEP,
//                    SoundEvents.METAL_PLACE, SoundEvents.METAL_HIT, SoundEvents.METAL_FALL);

    //@Override
    //public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
    //    SoundType soundType = getSoundType(state);
    //    if (entity != null && entity.getExtraCustomData()
    //            .method_10545("SilenceTankSound"))
    //        return SILENCED_METAL;
    //    return soundType;
    //}

    public enum Shape implements StringRepresentable {
        PLAIN, WINDOW, WINDOW_NW, WINDOW_SW, WINDOW_NE, WINDOW_SE;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        }
    }


}

