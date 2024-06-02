package com.weltonramos.notification.configuration.sqs;

import org.springframework.stereotype.Component;

@Component
public class Notifyer {

    private final MessageSender messageSender;

    public Notify(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void execute(Message message, Channel channel) {
        messageSender.send(message.getMessage(), channel.getChannelURL());
    }
}

