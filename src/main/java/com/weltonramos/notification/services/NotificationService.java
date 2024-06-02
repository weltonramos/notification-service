package com.weltonramos.notification.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weltonramos.notification.dto.NotificationData;
import com.weltonramos.notification.dto.NotificationResponse;

public interface NotificationService {

    NotificationResponse sendMessage(NotificationData notification) throws JsonProcessingException;

}
