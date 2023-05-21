package org.valkyrienskies.rnd.platform.api.network;

import net.minecraft.network.FriendlyByteBuf;

public interface VSCPacket {

    void write(FriendlyByteBuf buffer);

}
