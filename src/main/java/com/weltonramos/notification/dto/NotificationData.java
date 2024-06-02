package com.weltonramos.notification.dto;

import com.weltonramos.notification.domain.Message;
import com.weltonramos.notification.services.MessageChannel;

import java.util.Set;

public record NotificationData(String userId, Set<MessageChannel> communicationChannels, Message message) {
}
