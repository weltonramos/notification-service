package com.weltonramos.notification.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weltonramos.notification.dto.NotificationData;
import com.weltonramos.notification.dto.NotificationResponse;
import com.weltonramos.notification.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> sendMessage(@RequestBody NotificationData notificationData) throws JsonProcessingException {
        return ResponseEntity.ok(service.sendMessage(notificationData));
    }
}
