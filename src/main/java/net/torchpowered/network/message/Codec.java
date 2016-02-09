package net.torchpowered.network.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Represents a message codec for decoding/encoding messages.
 *
 * DECODE: Bytes -> DataInputStream -> Codec -> Message
 * ENCODE: Message -> Codec -> DataInputStream -> Bytes
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface Codec<A extends Message> {

    /**
     * Decodes a {@link DataInputStream} to a {@link Message}.
     *
     * @param stream the data of the message
     * @return the message
     */
    A decode(DataInputStream stream) throws Exception;

    /**
     * Encodes a {@link Message} to bytes to the {@link DataInputStream}.
     *
     * @param stream the stream where bytes should go into
     * @param message the message to encode
     * @return the bytes of the encoded message
     */
    DataOutputStream encode(DataOutputStream stream, A message) throws Exception;
}
