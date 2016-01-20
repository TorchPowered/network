package net.torchpowered.network.connection;

import java.io.DataInputStream;

/**
 * Represents a listener for connections.
 */
public interface ConnectionListener {
    void onConnect(SocketHandler handler, DataInputStream connection);

    void onCaughtException(Exception exception);
}
