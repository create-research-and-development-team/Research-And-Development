package org.valkyrienskies.vsrnd.util.fluid;

import com.simibubi.create.foundation.fluid.SmartFluidTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public interface VSCFluidTank {

	default int getSpaceLeft() {
		return Math.max(0, getTotalCapacity() - getCurrentAmount());
	}

	int getTotalCapacity();

	int getCurrentAmount();

	Fluid getFluidType();

	default boolean isEmpty() {
		return getCurrentAmount() <= 0;
	}

	CompoundTag store(CompoundTag tag);

	void read(CompoundTag tag);

	void shrink(int drainAmount);

	void grow(int fillAmount);

	default SmartFluidTank asSmartFluidTank() {
		return (SmartFluidTank) this;
	}
}
