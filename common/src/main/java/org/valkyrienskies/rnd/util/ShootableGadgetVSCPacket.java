package org.valkyrienskies.rnd.util;

import com.simibubi.create.content.curiosities.zapper.ShootGadgetPacket;
import com.simibubi.create.content.curiosities.zapper.ShootableGadgetRenderHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.rnd.platform.api.network.ClientNetworkContext;
import org.valkyrienskies.rnd.platform.api.network.S2CCWPacket;

public abstract class ShootableGadgetVSCPacket extends ShootGadgetPacket implements S2CCWPacket {

    public Vec3 location;
    public InteractionHand hand;
    public boolean self;

    public ShootableGadgetVSCPacket(Vec3 location, InteractionHand hand, boolean self) {
        super(location, hand, self);
        this.location = location;
        this.hand = hand;
        this.self = self;
    }

    public ShootableGadgetVSCPacket(FriendlyByteBuf buffer) {
        super(buffer);
        hand = buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        self = buffer.readBoolean();
        location = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        readAdditional(buffer);
    }

    protected abstract void readAdditional(FriendlyByteBuf buffer);

    protected abstract void writeAdditional(FriendlyByteBuf buffer);

    @Environment(EnvType.CLIENT)
    protected abstract void handleAdditional();

    @Environment(EnvType.CLIENT)
    protected abstract ShootableGadgetRenderHandler getHandler();

    @Override
    @Environment(EnvType.CLIENT)
    public final void handle(ClientNetworkContext ctx) {
        ctx.enqueueWork(() -> {
                    Entity renderViewEntity = Minecraft.getInstance()
                            .getCameraEntity();
                    if (renderViewEntity == null)
                        return;
                    if (renderViewEntity.position()
                            .distanceTo(location) > 100)
                        return;

                    ShootableGadgetRenderHandler handler = getHandler();
                    handleAdditional();
                    if (self)
                        handler.shoot(hand, location);
//                    else
//                        handler.playSound(hand, location);
                });
        ctx.setPacketHandled(true);
    }

}
