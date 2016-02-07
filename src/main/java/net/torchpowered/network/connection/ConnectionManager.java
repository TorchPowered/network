package net.torchpowered.network.connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a connection manager
 */
public class ConnectionManager implements Runnable {
    private ConnectionListener listener;
    private ServerSocket server;

    public ConnectionManager(ServerSocket server, ConnectionListener listener) {
        this.listener = listener;
        this.server = server;
    }

    public void run() {
        try {
            Socket connectionSocket = server.accept();
            this.listener.onConnect(connectionSocket, new DataInputStream(connectionSocket.getInputStream()));
        } catch (Exception e) {
            this.listener.onCaughtException(e);
        }
    }
}
