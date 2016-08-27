package net.take.messaginghub;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.limeprotocol.*;
import org.limeprotocol.client.ClientChannel;
import org.limeprotocol.client.ClientChannelImpl;
import org.limeprotocol.network.ChannelExtensions;
import org.limeprotocol.network.Transport;
import org.limeprotocol.network.tcp.SocketTcpClientFactory;
import org.limeprotocol.network.tcp.TcpTransport;
import org.limeprotocol.security.PlainAuthentication;
import org.limeprotocol.serialization.JacksonEnvelopeSerializer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Predicate;

public class MessagingHubClientImpl implements MessagingHubClient {

    private final ConnectionInfo connectionInfo;
    private ClientChannel clientChannel;

    public MessagingHubClientImpl(@NotNull ConnectionInfo connectionInfo) {

        this.connectionInfo = connectionInfo;
    }

    @Override
    public synchronized void start() throws IOException {

        Transport transport = new TcpTransport(
                new JacksonEnvelopeSerializer(),
                new SocketTcpClientFactory());

        try {
            transport.open(new URI(String.format("net.tcp://%s:%d", connectionInfo.hostName, connectionInfo.port)));
            clientChannel = new ClientChannelImpl(transport, true, true, true);

            clientChannel.establishSession(
                    connectionInfo.compression,
                    connectionInfo.encryption,
                    new Identity(connectionInfo.identifier, connectionInfo.domain),
                    new PlainAuthentication() {{
                        setPassword(connectionInfo.password);
                    }},
                    connectionInfo.instance,
                    new ClientChannel.EstablishSessionListener() {
                        @Override
                        public void onFailure(Exception exception) {

                        }

                        @Override
                        public void onReceiveSession(Session session) {

                        }
                    });


        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void stop() {

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
