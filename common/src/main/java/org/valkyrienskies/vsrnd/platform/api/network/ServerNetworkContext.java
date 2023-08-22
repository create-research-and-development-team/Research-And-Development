package org.valkyrienskies.vsrnd.platform.api.network;

import net.minecraft.server.level.ServerPlayer;

public interface ServerNetworkContext extends NetworkContext {

	ServerPlayer getSender();

}
