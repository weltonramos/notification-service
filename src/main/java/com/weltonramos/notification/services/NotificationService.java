package com.weltonramos.notification.services;

import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.NotificationResponse;
import com.weltonramos.notification.dto.UserPreferentecesDto;

public interface NotificationService {

    void createUserPreferences(UserPreferentecesDto userPreferences);

    NotificationResponse sendNotification(Notification notification);

    void updateOptOut(String userId, UserPreferentecesDto userPreferences);

}
