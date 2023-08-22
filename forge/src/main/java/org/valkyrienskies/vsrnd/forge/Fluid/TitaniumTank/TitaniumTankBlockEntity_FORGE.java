package org.valkyrienskies.vsrnd.forge.Fluid.TitaniumTank;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlockEntity;

public class TitaniumTankBlockEntity_FORGE extends TitaniumTankBlockEntity {
	public TitaniumTankBlockEntity_FORGE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}


	@Override
	public TitaniumTankBlockEntity_FORGE getControllerBE() {
		if (isController()) {
			return this;
		}
		BlockEntity blockEntity = level.getBlockEntity(controller);
		if (blockEntity instanceof TitaniumTankBlockEntity) {
			return (TitaniumTankBlockEntity_FORGE) blockEntity;
		}
		return null;
	}
}
