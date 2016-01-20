package net.torchpowered.network.utils;

import java.io.DataInputStream;

/**
 * Utility to get var values
 */
public class VarUtil {
    public static int readVarInt(DataInputStream in) {
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
            return 0;
        }
    }
}
