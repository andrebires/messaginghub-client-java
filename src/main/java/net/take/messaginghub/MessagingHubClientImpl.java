package net.take.messaginghub;

import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.limeprotocol.*;
import org.limeprotocol.client.ClientChannel;
import org.limeprotocol.client.ClientChannelImpl;
import org.limeprotocol.network.ChannelExtensions;
import org.limeprotocol.network.LimeException;
import org.limeprotocol.network.Transport;
import org.limeprotocol.network.tcp.SocketTcpClientFactory;
import org.limeprotocol.network.tcp.TcpTransport;
import org.limeprotocol.security.PlainAuthentication;
import org.limeprotocol.serialization.JacksonEnvelopeSerializer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class MessagingHubClientImpl implements MessagingHubClient {

    private final ConnectionInfo connectionInfo;
    private ClientChannel clientChannel;

    public MessagingHubClientImpl(@NotNull ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    @Override
    public synchronized void start(int timeoutSeconds) throws IOException {
        if (isListening()) throw new IllegalStateException("The client is already started");

        Transport transport = new TcpTransport(
                new JacksonEnvelopeSerializer(),
                new SocketTcpClientFactory());

        final Session[] resultSession = {null};
        final Exception[] resultException = {null};

        try {
            transport.open(connectionInfo.getUri());
            clientChannel = new ClientChannelImpl(transport, true, true, true);

            Semaphore semaphore = new Semaphore(1);
            semaphore.acquire();
            clientChannel.establishSession(
                    connectionInfo.compression,
                    connectionInfo.encryption,
                    connectionInfo.getIdentity(),
                    connectionInfo.getAuthentication(),
                    connectionInfo.instance,
                    new ClientChannel.EstablishSessionListener() {
                        @Override
                        public void onFailure(Exception exception) {
                            resultException[0] = exception;
                            semaphore.release();
                        }

                        @Override
                        public void onReceiveSession(Session session) {
                            resultSession[0] = session;
                            semaphore.release();
                        }
                    });

            if (!semaphore.tryAcquire(timeoutSeconds, TimeUnit.SECONDS)) {
                throw new RuntimeException("The connection has timed out");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (resultException[0] != null) throw new RuntimeException(resultException[0]);
        if (resultSession[0] != null && resultSession[0].getState() == Session.SessionState.ESTABLISHED) {
            return;
        }

        if (resultSession[0] == null || resultSession[0].getReason() == null) {
            throw new IllegalStateException("An invalid result was received by the server");
        }
        throw new LimeException(resultSession[0].getReason());
    }

    @Override
    public synchronized void stop() {
        if (!isListening()) throw new IllegalStateException("The client is not started");



    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void sendNotification(Notification notification) {

    }

    @Override
    public Command processCommand(Command requestCommand) {
        return null;
    }

    @Override
    public boolean isListening() {
        return clientChannel != null &&
               clientChannel.getState() == Session.SessionState.ESTABLISHED &&
               clientChannel.getTransport().isConnected();
    }

    @Override
    public synchronized MessagingHubClient addMessageReceiver(@NotNull MessageReceiver receiver, @Nullable Predicate<Message> filter, int priority) {
        return this;
    }

    @Override
    public synchronized MessagingHubClient addNotificationReceiver(@NotNull NotificationReceiver receiver, @Nullable Predicate<Message> filter, int priority) {
        return this;
    }
}
