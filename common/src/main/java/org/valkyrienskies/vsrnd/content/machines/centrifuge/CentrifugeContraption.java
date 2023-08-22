package org.valkyrienskies.vsrnd.content.machines.centrifuge;

import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.ContraptionType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import static com.simibubi.create.content.contraptions.ContraptionType.BEARING;

public class CentrifugeContraption extends Contraption {


	@Override
	public boolean assemble(Level world, BlockPos pos) throws AssemblyException {
		return false;
	}

	@Override
	public boolean canBeStabilized(Direction facing, BlockPos localPos) {
		return false;
	}

	@Override
	public ContraptionType getType() {
		return BEARING;
	}
}
