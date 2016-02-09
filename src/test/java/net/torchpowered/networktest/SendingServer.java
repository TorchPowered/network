package net.torchpowered.networktest;

import net.torchpowered.network.Server;
import net.torchpowered.network.connection.ConnectionListener;
import net.torchpowered.network.utils.ByteUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A test server testing packet sending.
 */
public class SendingServer {
    private static void startServer() throws Exception {
        Server server = new Server(InetAddress.getLocalHost().getHostAddress(), 30000);
        server.setConnectionListener(new ConnectionListener() {
            public void onConnect(Socket socket, DataInputStream connection) throws Exception {
                int packetId = ByteUtilities.readVarInt(connection);

                OutputStream stream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(stream);

                // get recieved packet with field boolean and id
                if(packetId == 0x00) {
                    ByteUtilities.writeVarInt(dataOutputStream, 0x00);
                    dataOutputStream.writeBoolean(true);
                    return;
                }
            }

            public void onCaughtException(Exception exception) {
                System.out.println("Test server received exception: " + exception.getMessage());
                exception.printStackTrace();
            }
        });
        server.startServer();
        server.startAcceptingConnections();
    }

    private static void startClient() throws Exception {
        Socket clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 30000));

        DataOutputStream clientOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream clientInputStream = new DataInputStream(clientSocket.getInputStream());

        // send packet
        ByteUtilities.writeVarInt(clientOutputStream, 0x00);

        // get response
        int packetId = ByteUtilities.readVarInt(clientInputStream);

        if(packetId == 0x00) {
            boolean response =  clientInputStream.readBoolean();
            System.out.println("Recieved packet with id of " + packetId + " and response boolean of " + response);
            if(!response) {
                System.out.println("Server is not ready for connections!");
                clientOutputStream.close();
                clientInputStream.close();
                clientSocket.close();
                return;
            }
        } else {
            System.out.println("Invalid server! (server sent a invalid packet with a invalid packet id)");
            clientOutputStream.close();
            clientInputStream.close();
            clientSocket.close();
            return;
        }

        System.out.println("Test is success!");
    }

    public static void main(String[] args) throws Exception {
        startServer();
        startClient();
    }
}
