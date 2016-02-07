package net.torchpowered.network.connection;

import java.io.DataInputStream;
import java.net.Socket;

/**
 * Represents a listener for connections.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface ConnectionListener {
    void onConnect(Socket socket, DataInputStream connection) throws Exception;

    void onCaughtException(Exception exception);
}
