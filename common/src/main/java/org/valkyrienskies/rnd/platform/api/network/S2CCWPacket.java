package org.valkyrienskies.rnd.platform.api.network;

public interface S2CCWPacket extends VSCPacket {

    void handle(ClientNetworkContext context);

}
