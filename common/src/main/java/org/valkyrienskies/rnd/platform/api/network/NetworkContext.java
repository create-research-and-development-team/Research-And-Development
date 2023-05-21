package org.valkyrienskies.rnd.platform.api.network;

public interface NetworkContext {

    void enqueueWork(Runnable runnable);

    default void handled() {
        setPacketHandled(true);
    }

    void setPacketHandled(boolean value);
}
