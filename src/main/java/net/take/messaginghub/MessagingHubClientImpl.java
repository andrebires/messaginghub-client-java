package net.take.messaginghub;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.limeprotocol.Command;
import org.limeprotocol.Message;
import org.limeprotocol.Notification;

import java.util.function.Predicate;

public class MessagingHubClientImpl implements MessagingHubClient {

    @Override
    public void start() {

    }

    @Override
    public void stop() {

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
        return false;
    }

    @Override
    public MessagingHubClient addMessageReceiver(@NotNull MessageReceiver receiver, @Nullable Predicate<Message> filter, int priority) {
        return this;
    }

    @Override
    public MessagingHubClient addNotificationReceiver(@NotNull NotificationReceiver receiver, @Nullable Predicate<Message> filter, int priority) {
        return this;
    }
}
