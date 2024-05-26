package com.weltonramos.notification.services;

import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.UserPreferentecesDto;
import com.weltonramos.notification.exception.MessageNotificationNowAllowedException;
import com.weltonramos.notification.exception.UserNotFoundException;
import com.weltonramos.notification.repository.NotificationRepository;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationServiceImpl implements NotificationService {

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
                .createdAt(Instant.now().toString())
                .build();

        repository.save(preferences);
    }

    @Override
    public void sendNotification(Notification notification) {

        String userId = notification.getUserId();
        UserPreferencesEntity userPreferences = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));

        if (!userPreferences.isOptOut())
            throw new MessageNotificationNowAllowedException("Customer " + userId + " do not allow notifications.");

        notification.getNotificationChannel()
                .forEach(item -> sqsTemplate.send(emailMessageQueueUrl, notification));
    }

    @Override
    public void updateOptOut(String userId) {
        UserPreferencesEntity userPreferencesEntity = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));

        userPreferencesEntity.setOptOut(!userPreferencesEntity.isOptOut());
        userPreferencesEntity.setUpdatedAt(Instant.now().toString());

        repository.save(userPreferencesEntity);
    }
}
