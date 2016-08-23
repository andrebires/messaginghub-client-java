package net.take.messaginghub;


import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.limeprotocol.Message;

import java.util.function.Predicate;

public interface MessagingHubClient extends Startable, Stoppable {

    void addMessageReceiver(@NotNull MessageReceiver receiver, @Nullable Predicate<Message> filter);

    void addNotificationReceiver(@NotNull NotificationReceiver receiver, @Nullable Predicate<Message> filter);
}
