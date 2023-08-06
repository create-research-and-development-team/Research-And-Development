package org.valkyrienskies.vsrnd.content.contraptions.centrifuge;

import com.simibubi.create.content.contraptions.bearing.BearingBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CentrifugeBlock extends BearingBlock implements IBE<CentrifugeBlockEntity> {
    public CentrifugeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<CentrifugeBlockEntity> getBlockEntityClass() {
        return null;
    }

    @Override
    public BlockEntityType<CentrifugeBlockEntity> getBlockEntityType() {
        return null;
    }
    //this needs to be eased in and out using the util potato made
}
