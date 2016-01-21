package net.torchpowered.network.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Represents a packet
 */
public class Packet {
    private ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private DataOutputStream packetData = new DataOutputStream(stream);

    public void writeVarInt(int paramInt) {
        DataOutputStream out = packetData;
        try {
            while (true) {
                if ((paramInt & 0xFFFFFF80) == 0) {
                    out.writeByte(paramInt);
                    return;
                }

                out.writeByte(paramInt & 0x7F | 0x80);
                paramInt >>>= 7;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getData() {
        return packetData;
    }

    public ByteArrayOutputStream getPacketData() {
        return stream;
    }
}
