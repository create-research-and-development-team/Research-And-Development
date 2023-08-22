package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class TitaniumTankBlockEntity extends FluidTankBlockEntity {


	public TitaniumTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);

	}

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

//    @Override
//    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
//        TitaniumTankBlockEntity controllerBE = getControllerBE();
//        if (controllerBE == null)
//            return false;
//        return containedFluidTooltip(tooltip, isPlayerSneaking,
//                controllerBE.getFluidStorage(null));
//    }


}