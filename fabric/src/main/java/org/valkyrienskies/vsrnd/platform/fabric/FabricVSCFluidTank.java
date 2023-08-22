package org.valkyrienskies.vsrnd.platform.fabric;

import com.simibubi.create.foundation.fluid.SmartFluidTank;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import org.valkyrienskies.vsrnd.util.fluid.VSCFluidTank;

import java.util.function.Consumer;

public class FabricVSCFluidTank extends SmartFluidTank implements VSCFluidTank {
	private final static long FLUID_PER_MB = FluidConstants.BUCKET / 1000;

	public FabricVSCFluidTank(long capacity, Consumer<FluidStack> updateCallback) {
		super(capacity, updateCallback);
	}

	@Override
	public int getSpaceLeft() {
		return (int) (getSpace() / FLUID_PER_MB);
	}

	@Override
	public int getTotalCapacity() {
		return (int) (super.getCapacity() / FLUID_PER_MB);
	}

	@Override
	public int getCurrentAmount() {
		return (int) (getAmount() / FLUID_PER_MB);
	}

	@Override
	public Fluid getFluidType() {
		return getFluid().getFluid();
	}

	@Override
	public CompoundTag store(CompoundTag tag) {
		return super.writeToNBT(tag);
	}

	@Override
	public void read(CompoundTag tag) {
		super.readFromNBT(tag);
	}

	@Override
	public void shrink(int drainAmount) {
		amount -= FLUID_PER_MB * drainAmount;
		updateStack();
	}

	@Override
	public void grow(int fillAmount) {
		amount += FLUID_PER_MB * fillAmount;
		updateStack();
	}

	private void updateStack() {
		this.stack = new FluidStack(this.variant, this.amount);
	}
}
