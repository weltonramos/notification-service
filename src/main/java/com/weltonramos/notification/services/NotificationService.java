package com.weltonramos.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.NotificationResponse;
import com.weltonramos.notification.dto.UserPreferentecesDto;

public interface NotificationService {

    NotificationResponse sendNotification(Notification notification) throws JsonProcessingException;

}
