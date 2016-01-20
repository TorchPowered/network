package net.torchpowered.network;

import net.torchpowered.network.connection.ConnectionListener;
import net.torchpowered.network.connection.ConnectionManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Represents a server
 */
public class Server {
    private String ip;
    private int port;

    private ServerSocket serverSocket;

    private ConnectionListener listener;

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public void startServer() throws Exception {
       serverSocket = new ServerSocket(port, 50, InetAddress.getByName(getIp()));
    }

    public void setConnectionListener(ConnectionListener listener) {
        this.listener = listener;
    }

    public void startAcceptingConnections() {
        Thread connectionManager = new Thread(new ConnectionManager(serverSocket, listener));
        connectionManager.setName("Connection Manager");
        connectionManager.setDaemon(true);
        connectionManager.start();
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
