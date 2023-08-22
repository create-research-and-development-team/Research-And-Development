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
	public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", Shape.class);
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
	public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
								  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
		return pState;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		for (int i = 0; i < rotation.ordinal(); i++) {
			state = rotateOnce(state);
		}
		return state;
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		if (mirror == Mirror.NONE) {
			return state;
		}
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
	public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
		return Shapes.block();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos,
										CollisionContext pContext) {
		if (pContext == CollisionContext.empty()) {
			return CAMPFIRE_SMOKE_CLIP;
		}
		return pState.getShape(pLevel, pPos);
	}

	//@Override

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


	public enum Shape implements StringRepresentable {
		PLAIN,
		WINDOW,
		WINDOW_NW,
		WINDOW_SW,
		WINDOW_NE,
		WINDOW_SE;

		@Override
		public String getSerializedName() {
			return Lang.asId(name());
		}
	}


}

