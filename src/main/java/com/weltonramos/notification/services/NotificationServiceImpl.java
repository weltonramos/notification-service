package com.weltonramos.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.NotificationResponse;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${web.message.queue}")
    private String webMessageQueueUrl;

    @Value("${email.message.queue}")
    private String emailMessageQueueUrl;

    @Value("${sms.message.queue}")
    private String smsMessageQueueUrl;

    private final UserPreferencesService userPreferencesService;
    private final SqsTemplate sqsTemplate;

    public NotificationServiceImpl(UserPreferencesService userPreferencesService, SqsTemplate sqsTemplate) {
        this.userPreferencesService = userPreferencesService;
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public NotificationResponse sendNotification(Notification notification) throws JsonProcessingException {

        String userId = notification.getUserId();
        UserPreferencesEntity userPreferencesEntity = userPreferencesService.getUserPreferences(userId);

        if (userPreferencesEntity.isDisabledNotifications())
            return new NotificationResponse(String.format("User with ID %s do not allow messages.", userId));

        String messageInJson = convertToJson(notification);

        notification.getNotificationChannels()
                .forEach(item -> sqsTemplate.send(webMessageQueueUrl, messageInJson));

        return new NotificationResponse("Messages submeted.");
    }

    private static String convertToJson(Notification notification) throws JsonProcessingException {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(notification);
    }
}
