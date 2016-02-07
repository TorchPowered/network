package net.torchpowered.networktest;

import net.torchpowered.network.Server;
import net.torchpowered.network.connection.ConnectionListener;
import net.torchpowered.network.utils.ByteUtilities;

import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * A test server.
 */
public class MyServer {
    public static void main(String[] args) {
        try {
            Server server = new Server(InetAddress.getLocalHost().getHostAddress(), 25565);
            server.setConnectionListener(new ConnectionListener() {
                public void onConnect(Socket handler, DataInputStream connection) throws Exception {
                    //header
                    int packetSize = ByteUtilities.readVarInt(connection);
                    int packetId = ByteUtilities.readVarInt(connection);
                    System.out.println("Client has connected with packet size of " + packetSize + " and a ID of " + packetId);

                    //packet identification
                    if(packetId == 0x00) {
                        int protocolVersion = ByteUtilities.readVarInt(connection);
                        String address = ByteUtilities.readUTF8(connection);
                        int port = connection.readUnsignedShort();
                        int nextState = ByteUtilities.readVarInt(connection);
                        System.out.println("Recieved client connection that sent a handshake with protocol version " + protocolVersion);
                        System.out.println("Address: " + address + ", port: " + port + ", next state: " + nextState);
                    }
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
