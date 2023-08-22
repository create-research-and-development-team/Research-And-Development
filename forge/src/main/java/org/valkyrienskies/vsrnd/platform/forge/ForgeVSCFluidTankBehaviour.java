package org.valkyrienskies.vsrnd.platform.forge;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.valkyrienskies.vsrnd.util.fluid.VSCFluidTank;
import org.valkyrienskies.vsrnd.util.fluid.VSCFluidTankBehaviour;

import java.util.function.Consumer;

public class ForgeVSCFluidTankBehaviour extends VSCFluidTankBehaviour {
	private LazyOptional<? extends IFluidHandler> capability;

	protected ForgeVSCFluidTankBehaviour(BehaviourType<VSCFluidTankBehaviour> type, SmartBlockEntity te, int tanks, long tankCapacity, boolean enforceVariety) {
		super(type, te, tanks, tankCapacity, enforceVariety);

		IFluidHandler[] handlers = new IFluidHandler[tanks];

		for (int i = 0; i < tanks; ++i) {
			handlers[i] = (ForgeVSCFluidTank) this.tanks[i].getTank();
		}

		this.capability = LazyOptional.of(() -> new InternalFluidHandler(handlers, enforceVariety));
	}


	@Override
	protected VSCFluidTank makeFluidTank(long capacity, Consumer<Fluid> updateCallback) {
		return new ForgeVSCFluidTank((int) capacity, f -> updateCallback.accept(f.getFluid()));
	}

	public LazyOptional<? extends IFluidHandler> getCapability() {
		return capability;
	}

	private class InternalFluidHandler extends CombinedTankWrapper {
		public InternalFluidHandler(IFluidHandler[] handlers, boolean enforceVariety) {
			super(handlers);
			if (enforceVariety) {
				this.enforceVariety();
			}

		}

		public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
			return !insertionAllowed ? 0 : super.fill(resource, action);
		}

		public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
			return !extractionAllowed ? FluidStack.EMPTY : super.drain(resource, action);
		}

		public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
			return !extractionAllowed ? FluidStack.EMPTY : super.drain(maxDrain, action);
		}

		public int forceFill(FluidStack resource, IFluidHandler.FluidAction action) {
			return super.fill(resource, action);
		}
	}
}
