package org.valkyrienskies.vsrnd.content.Fluids.FermentingTank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class FermentingTankBlockEntity extends FluidTankBlockEntity {


	public FermentingTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@SuppressWarnings("unchecked")


	protected void updateConnectivity() {
		updateConnectivity = false;
		if (level.isClientSide) {
			return;
		}
		if (!isController()) {
			return;
		}
		ConnectivityHandler.formMulti(this);
	}

	@Override
	public void updateBoilerState() {
		return;
	}


}
