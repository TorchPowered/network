package net.torchpowered.networktest;

import net.torchpowered.network.Server;
import net.torchpowered.network.connection.ConnectionListener;
import net.torchpowered.network.connection.SocketHandler;
import net.torchpowered.network.utils.VarUtil;

import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A test server.
 */
public class MyServer {
    public static void main(String[] args) {
        try {
            Server server = new Server(InetAddress.getLocalHost().getHostAddress(), 25565);
            server.setConnectionListener(new ConnectionListener() {
                public void onConnect(SocketHandler handler, DataInputStream connection) {
                    System.out.println("Yay, packet recieved with packet size of " + VarUtil.readVarInt(connection) + " and id of " + VarUtil.readVarInt(connection));
                }

                public void onCaughtException(Exception exception) {
                    System.out.println("Packet got a exception.");
                    exception.printStackTrace();
                }
            });
            server.startServer();
            server.startAcceptingConnections();
            while(true){}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
