package net.take.messaginghub;

import org.limeprotocol.Command;
import org.limeprotocol.Message;
import org.limeprotocol.Notification;

public interface MessagingHubSender {
    void sendMessage(Message message);

    void sendNotification(Notification notification);

    Command processCommand(Command requestCommand);
}
