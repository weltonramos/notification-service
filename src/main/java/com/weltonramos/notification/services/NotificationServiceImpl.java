package com.weltonramos.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.NotificationResponse;
import com.weltonramos.notification.dto.UserPreferentecesDto;
import com.weltonramos.notification.exception.UserNotFoundException;
import com.weltonramos.notification.repositories.NotificationRepository;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${web.message.queue}")
    private String webMessageQueueUrl;

    @Value("${email.message.queue}")
    private String emailMessageQueueUrl;

    @Value("${sms.message.queue}")
    private String smsMessageQueueUrl;

    private final NotificationRepository repository;
    private final SqsTemplate sqsTemplate;

    public NotificationServiceImpl(NotificationRepository repository, SqsTemplate sqsTemplate) {
        this.repository = repository;
        this.sqsTemplate = sqsTemplate;
    }

    @Override
    public void createUserPreferences(UserPreferentecesDto userPreferences) {

        UserPreferencesEntity preferences = UserPreferencesEntity.builder()
                .email(userPreferences.getEmail())
                .phoneNumber(userPreferences.getPhoneNumber())
                .optOut(userPreferences.isOptOut())
                .deviceToken(userPreferences.getDeviceToken())
                .createdAt(Instant.now())
                .build();

        repository.save(preferences);
    }

    @Override
    public NotificationResponse sendNotification(Notification notification) throws JsonProcessingException {

        String userId = notification.getUserId();
        UserPreferencesEntity userPreferencesEntity = getUserPreferences(userId);

        if (userPreferencesEntity.isDisabledNotifications())
            return new NotificationResponse(String.format("User %s do not allow messages.", userId));

        String messageInJson = jsonConverter(notification);

        notification.getNotificationChannels()
                .forEach(item -> sqsTemplate.send(webMessageQueueUrl, messageInJson));

        return new NotificationResponse("Messages submeted.");
    }

    private static String jsonConverter(Notification notification) throws JsonProcessingException {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(notification);
    }

    @Override
    public void updateUserPreferences(String userId, UserPreferentecesDto userPreferences) {

        UserPreferencesEntity userPreferencesEntity = getUserPreferences(userId);

        userPreferencesEntity.setEmail(userPreferences.getEmail());
        userPreferencesEntity.setPhoneNumber(userPreferences.getPhoneNumber());
        userPreferencesEntity.setOptOut(userPreferences.isOptOut());
        userPreferencesEntity.setUpdatedAt(Instant.now());

        repository.save(userPreferencesEntity);
    }

    private UserPreferencesEntity getUserPreferences(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));
    }
}
