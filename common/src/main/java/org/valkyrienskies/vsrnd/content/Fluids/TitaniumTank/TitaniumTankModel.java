package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;


import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.utility.Iterate;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class TitaniumTankModel extends CTModel {

	private TitaniumTankModel(BakedModel originalModel, CTSpriteShiftEntry side, CTSpriteShiftEntry top,
							  CTSpriteShiftEntry inner) {
		super(originalModel, new TitaniumTankCTBehaviour(side, top, inner));
	}

	public static TitaniumTankModel standard(BakedModel originalModel) {
		return new TitaniumTankModel(originalModel, AllSpriteShifts.FLUID_TANK, AllSpriteShifts.FLUID_TANK_TOP,
									 AllSpriteShifts.FLUID_TANK_INNER);
	}

	@Override
	public void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
		CullData cullData = new CullData();
		for (Direction d : Iterate.horizontalDirections) {
			cullData.setCulled(d, ConnectivityHandler.isConnected(blockView, pos, pos.relative(d)));
		}

		context.pushTransform(quad -> {
			Direction cullFace = quad.cullFace();
			if (cullFace != null && cullData.isCulled(cullFace)) {
				return false;
			}
			quad.cullFace(null);
			return true;
		});
		super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
		context.popTransform();
	}

	private static class CullData {
		boolean[] culledFaces;

		public CullData() {
			culledFaces = new boolean[4];
			Arrays.fill(culledFaces, false);
		}

		void setCulled(Direction face, boolean cull) {
			if (face.getAxis()
					.isVertical()) {
				return;
			}
			culledFaces[face.get2DDataValue()] = cull;
		}

		boolean isCulled(Direction face) {
			if (face.getAxis()
					.isVertical()) {
				return false;
			}
			return culledFaces[face.get2DDataValue()];
		}
	}

}