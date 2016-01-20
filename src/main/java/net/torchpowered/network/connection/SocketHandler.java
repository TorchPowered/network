package net.torchpowered.network.connection;

import net.torchpowered.network.packet.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Represents a socket handler
 */
public class SocketHandler {
    private OutputStream outputStream;

    public SocketHandler(Socket socket) {
        try {
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet){
        DataOutputStream socketStream = new DataOutputStream(outputStream);
        try {
            socketStream.write(packet.getPacketData().toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
