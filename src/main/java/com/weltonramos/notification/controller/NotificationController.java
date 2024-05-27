package com.weltonramos.notification.controller;

import com.weltonramos.notification.dto.Notification;
import com.weltonramos.notification.dto.NotificationResponse;
import com.weltonramos.notification.dto.UserPreferentecesDto;
import com.weltonramos.notification.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping("/user-preferences")
    public ResponseEntity<Void> createUserPreferences(@RequestBody UserPreferentecesDto userPreferences) {
        service.createUserPreferences(userPreferences);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(service.sendNotification(notification));
    }

    @PatchMapping("/user-preferences/{userId}")
    public ResponseEntity<Void> updateUserPreferences(@PathVariable("userId") String userId, @RequestBody UserPreferentecesDto userPreferentecesDto) {
        service.updateOptOut(userId, userPreferentecesDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
