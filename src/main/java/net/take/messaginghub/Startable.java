package net.take.messaginghub;

import java.io.IOException;

/**
 * Represents a type that can be started.
 */
public interface Startable {
    void start() throws IOException;
}
