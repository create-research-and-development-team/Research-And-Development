package org.valkyrienskies.vsrnd.content.contraptions.fish;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.valkyrienskies.vsrnd.platform.api.network.C2SVSCPacket;
import org.valkyrienskies.vsrnd.platform.api.network.ClientNetworkContext;
import org.valkyrienskies.vsrnd.platform.api.network.S2CCWPacket;

public class FishPacket implements  S2CCWPacket {

    private final BlockPos pos;

    private final float Headangle;
    private final float Tailangle;
    private final float Flipperangle;
    private final float Jawangle;
    public FishPacket(FishBlockEntity ce) {
        pos = ce.getBlockPos();
        Headangle = ce.GetHeadAngle();
        Tailangle = ce.GetTailAngle();
        Flipperangle = ce.GetFlipperAngle();
        Jawangle = ce.GetJawAngle();
    }

    @Override
    public void handle(ClientNetworkContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.getBlockEntity(pos) instanceof FishBlockEntity) {
                FishBlockEntity FBE = (FishBlockEntity) Minecraft.getInstance().level.getBlockEntity(pos);
                if (FBE != null) {
                    FBE.SetHeadAngle(Headangle);
                    FBE.SetTailAngle(Tailangle);
                    FBE.SetFlipperAngle(Flipperangle);
                    FBE.SetJawAngle(Jawangle);
                    FBE.setChanged();
                }
            }

        });
        context.setPacketHandled(true);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        CompoundTag nbt = new CompoundTag();
        nbt.putFloat("VSrnd$headangle", Headangle);
        nbt.putFloat("VSrnd$tailangle", Tailangle);
        nbt.putFloat("VSrnd$flipperangle", Flipperangle);
        nbt.putFloat("VSrnd$jawangle", Jawangle);
        buffer.writeNbt(nbt);
    }

    public FishPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();

        CompoundTag nbt = buffer.readNbt();

        Headangle = nbt.getFloat("VSrnd$headangle");
        Tailangle = nbt.getFloat("VSrnd$tailangle");
        Flipperangle = nbt.getFloat("VSrnd$flipperangle");
        Jawangle = nbt.getFloat("VSrnd$jawangle");
    }

}
