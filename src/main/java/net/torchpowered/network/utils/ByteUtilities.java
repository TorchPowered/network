package net.torchpowered.network.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * This class contains utilities for decoding/encoding bytes.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class ByteUtilities {

    /**
     * Reads a VarInt from a DataInputStream's bytes.
     *
     * @param in the DataInputStream to read the bytes from.
     * @return the VarInt from the DataInputStream
     */
    public static Integer readVarInt(DataInputStream in) {
        try {
            int i = 0;
            int j = 0;
            while (true) {
                int k = in.readByte();
                i |= (k & 0x7F) << j++ * 7;
                if (j > 5) throw new RuntimeException("VarInt too big");
                if ((k & 0x80) != 128) break;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes a VarInt to a DataOutputStream's bytes.
     *
     * @param out the DataOutputStream to write the bytes to
     * @param paramInt the VarInt that will be written to the DataOutputStream
     */
    public void writeVarInt(DataOutputStream out, int paramInt) {
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

    /**
     * Reads a UTF-8 String from a DataInputStream's bytes.
     *
     * @param in the DataInputStream to read the bytes from.
     * @return the UTF-8 String from the DataInputStream
     */
    public String readUTF8(DataInputStream in) {
        try {
            int len = readVarInt(in);
            byte[] bytes = new byte[len];
            in.readFully(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes a UTF-8 String to a DataOutputStream's bytes.
     *
     * @param out the DataOutputStream to write the bytes to
     * @param string the String that will be written to the DataOutputStream
     */
    public void writeUTF8(DataOutputStream out, String string) {
        try {
            byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
            if (bytes.length >= Short.MAX_VALUE) {
                throw new RuntimeException("Attempt to write a string with a length greater than Short.MAX_VALUE to ByteBuf!");
            }
            writeVarInt(out, bytes.length);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
