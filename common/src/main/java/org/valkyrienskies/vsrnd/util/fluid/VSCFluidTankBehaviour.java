package org.valkyrienskies.vsrnd.util.fluid;


import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.mutable.MutableInt;
import org.valkyrienskies.vsrnd.mixin.accessors.FluidAccessor;
import org.valkyrienskies.vsrnd.platform.PlatformUtils;

import java.util.function.Consumer;

public abstract class VSCFluidTankBehaviour extends BlockEntityBehaviour {

	public static final BehaviourType<VSCFluidTankBehaviour>
			TYPE = new BehaviourType(), INPUT = new BehaviourType("Input"), OUTPUT = new BehaviourType("Output");

	private static final int SYNC_RATE = 8;

	protected int syncCooldown;
	protected boolean queuedSync;
	protected TankSegment[] tanks;
	protected boolean extractionAllowed;
	protected boolean insertionAllowed;
	protected Runnable fluidUpdateCallback;

	private BehaviourType<VSCFluidTankBehaviour> behaviourType;

	protected VSCFluidTankBehaviour(BehaviourType<VSCFluidTankBehaviour> type, SmartBlockEntity te, int tanks,
									long tankCapacity, boolean enforceVariety) {
		super(te);
		insertionAllowed = true;
		extractionAllowed = true;
		behaviourType = type;
		this.tanks = new TankSegment[tanks];
		for (int i = 0; i < tanks; i++) {
			this.tanks[i] = new TankSegment(tankCapacity);
		}

		fluidUpdateCallback = () -> {
		};
	}

	public static VSCFluidTankBehaviour single(SmartBlockEntity te, long capacity) {
		return PlatformUtils.cwFluidTank(TYPE, te, 1, capacity, false);
	}

	protected abstract VSCFluidTank makeFluidTank(long capacity, Consumer<Fluid> updateCallback);

	public VSCFluidTankBehaviour whenFluidUpdates(Runnable fluidUpdateCallback) {
		this.fluidUpdateCallback = fluidUpdateCallback;
		return this;
	}

	public VSCFluidTankBehaviour allowInsertion() {
		insertionAllowed = true;
		return this;
	}

	public VSCFluidTankBehaviour allowExtraction() {
		extractionAllowed = true;
		return this;
	}

	public VSCFluidTankBehaviour forbidInsertion() {
		insertionAllowed = false;
		return this;
	}

	public VSCFluidTankBehaviour forbidExtraction() {
		extractionAllowed = false;
		return this;
	}

	public void sendDataImmediately() {
		syncCooldown = 0;
		queuedSync = false;
		updateFluids();
	}	@Override
	public void initialize() {
		super.initialize();
		if (getWorld().isClientSide) {
			return;
		}
		forEach(ts -> {
			ts.fluidLevel.forceNextSync();
			ts.onFluidChanged();
		});
	}

	public void sendDataLazily() {
		if (syncCooldown > 0) {
			queuedSync = true;
			return;
		}
		updateFluids();
		queuedSync = false;
		syncCooldown = SYNC_RATE;
	}	@Override
	public void tick() {
		super.tick();

		if (syncCooldown > 0) {
			syncCooldown--;
			if (syncCooldown == 0 && queuedSync) {
				updateFluids();
			}
		}

		forEach(te -> {
			LerpedFloat fluidLevel = te.getFluidLevel();
			if (fluidLevel != null) {
				fluidLevel.tickChaser();
			}
		});
	}

	public VSCFluidTank getPrimaryHandler() {
		return getPrimaryTank().tank;
	}

	public TankSegment getPrimaryTank() {
		return tanks[0];
	}

	public TankSegment[] getTanks() {
		return tanks;
	}	protected void updateFluids() {
		fluidUpdateCallback.run();
		blockEntity.sendData();
		blockEntity.setChanged();
	}

	public boolean isEmpty() {
		for (TankSegment tankSegment : tanks) {
			if (!tankSegment.tank.isEmpty()) {
				return false;
			}
		}
		return true;
	}	@Override
	public void unload() {
		super.unload();
	}

	public class TankSegment {

		protected VSCFluidTank tank;
		protected LerpedFloat fluidLevel;
		protected Fluid renderedFluid;

		public TankSegment(long capacity) {
			tank = makeFluidTank(capacity, f -> onFluidChanged());
			fluidLevel = LerpedFloat.linear()
									.startWithValue(0)
									.chase(0, .25, LerpedFloat.Chaser.EXP);
		}

		public void onFluidChanged() {
			if (!blockEntity.hasLevel()) {
				return;
			}
			fluidLevel.chase(tank.getCurrentAmount() / (float) tank.getTotalCapacity(), .25, LerpedFloat.Chaser.EXP);
			if (!getWorld().isClientSide) {
				sendDataLazily();
			}
			if (blockEntity.isVirtual() && !tank.isEmpty()) {
				renderedFluid = tank.getFluidType();
			}
		}

		public LerpedFloat getFluidLevel() {
			return fluidLevel;
		}

		public CompoundTag writeNBT() {
			CompoundTag compound = new CompoundTag();
			compound.put("TankContent", tank.store(new CompoundTag()));
			compound.put("Level", fluidLevel.writeNBT());
			return compound;
		}

		public void readNBT(CompoundTag compound, boolean clientPacket) {
			tank.read(compound.getCompound("TankContent"));
			fluidLevel.readNBT(compound.getCompound("Level"), clientPacket);
			if (!tank.isEmpty()) {
				renderedFluid = tank.getFluidType();
			}
		}

		public boolean isEmpty(float partialTicks) {
			Fluid renderedFluid = getRenderedFluid();
			if (((FluidAccessor) renderedFluid).getIfEmpty()) {
				return true;
			}

			float units = getTotalUnits(partialTicks);
			if (units < 1) {
				return true;
			}

			return false;
		}

		public Fluid getRenderedFluid() {
			return renderedFluid;
		}

		public float getTotalUnits(float partialTicks) {
			return fluidLevel.getValue(partialTicks) * tank.getTotalCapacity();
		}

		public VSCFluidTank getTank() {
			return tank;
		}
	}







	public void forEach(Consumer<TankSegment> action) {
		for (TankSegment tankSegment : tanks) {
			action.accept(tankSegment);
		}
	}

	@Override
	public void write(CompoundTag nbt, boolean clientPacket) {
		super.write(nbt, clientPacket);
		ListTag tanksNBT = new ListTag();
		forEach(ts -> tanksNBT.add(ts.writeNBT()));
		nbt.put(getType().getName() + "Tanks", tanksNBT);
	}

	@Override
	public void read(CompoundTag nbt, boolean clientPacket) {
		super.read(nbt, clientPacket);
		MutableInt index = new MutableInt(0);
		NBTHelper.iterateCompoundList(nbt.getList(getType().getName() + "Tanks", Tag.TAG_COMPOUND), c -> {
			if (index.intValue() >= tanks.length) {
				return;
			}
			tanks[index.intValue()].readNBT(c, clientPacket);
			index.increment();
		});
	}



	@Override
	public BehaviourType<?> getType() {
		return behaviourType;
	}
}
