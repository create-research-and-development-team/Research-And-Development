package org.valkyrienskies.vsrnd.platform.api.network;

import net.minecraft.network.FriendlyByteBuf;

public interface VSCPacket {

	void write(FriendlyByteBuf buffer);

}
