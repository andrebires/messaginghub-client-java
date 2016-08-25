package net.take.messaginghub;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.limeprotocol.Message;

import java.util.function.Predicate;

public interface MessagingHubListener extends Startable, Stoppable {

    boolean isListening();

    MessagingHubClient addMessageReceiver(@NotNull MessageReceiver receiver, @Nullable Predicate<Message> filter, int priority);

    MessagingHubClient addNotificationReceiver(@NotNull NotificationReceiver receiver, @Nullable Predicate<Message> filter, int priority);

    default MessagingHubClient addMessageReceiver(@NotNull MessageReceiver receiver) {
        return addMessageReceiver(receiver, null);
    }

    default MessagingHubClient addMessageReceiver(@NotNull MessageReceiver receiver, @NotNull Predicate<Message> filter) {
        return addMessageReceiver(receiver, filter, 0);
    }

    default MessagingHubClient addNotificationReceiver(@NotNull NotificationReceiver receiver) {
        return addNotificationReceiver(receiver, null);
    }

    default MessagingHubClient addNotificationReceiver(@NotNull NotificationReceiver receiver, @Nullable Predicate<Message> filter) {
        return addNotificationReceiver(receiver, filter, 0);
    }
}
