package com.weltonramos.notification.services;

import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.UserPreferentecesDto;

public interface NotificationService {

    void createUserPreferences(UserPreferentecesDto userPreferences);

    void sendNotification(Notification notification);

    void updateOptOut(String userId);

}
