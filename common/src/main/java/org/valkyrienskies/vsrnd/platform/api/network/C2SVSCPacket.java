package org.valkyrienskies.vsrnd.platform.api.network;

public interface C2SVSCPacket extends VSCPacket {

	void handle(ServerNetworkContext context);

}
