package net.take.messaginghub;

import org.limeprotocol.Envelope;

public interface EnvelopeReceiver<T extends Envelope> {
    void receive(T envelope);
}
