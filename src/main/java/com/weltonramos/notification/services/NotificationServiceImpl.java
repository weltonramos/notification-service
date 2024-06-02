package com.weltonramos.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weltonramos.notification.domain.Message;
import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.NotificationData;
import com.weltonramos.notification.dto.NotificationResponse;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${message.queue.web}")
    private String webQueue;

    @Value("${message.queue.email}")
    private String emailQueue;

    @Value("${message.queue.sms}")
    private String smsQueue;

    private final UserPreferencesService userPreferencesService;

    private final SqsTemplate sqsTemplate;

    public NotificationServiceImpl(UserPreferencesService userPreferencesService, SqsTemplate sqsTemplate) {
        this.userPreferencesService = userPreferencesService;
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public NotificationResponse sendMessage(NotificationData notificationData) throws JsonProcessingException {

        //TODO: Melhorar a forma de obter a URL correta
        final Map<MessageChannel, String> channelsUrl = new EnumMap<>(MessageChannel.class);
        channelsUrl.put(MessageChannel.WEB, webQueue);
        channelsUrl.put(MessageChannel.EMAIL, emailQueue);
        channelsUrl.put(MessageChannel.SMS, smsQueue);

        String userId = notificationData.userId();
        UserPreferencesEntity userPreferencesEntity = userPreferencesService.getUserPreferences(userId);

        if (!userPreferencesEntity.isOptOutSigned())
            return new NotificationResponse(String.format("User with ID %s do not allow messages.", userId));

        String message = convertToJson(notificationData.message());

        notificationData.communicationChannels().forEach(channel ->
                sqsTemplate.send(channelsUrl.get(channel), message));

        return new NotificationResponse("Messages submeted.");
    }

    private static String convertToJson(Message message) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(message);
    }
}
