package com.weltonramos.notification.controller;

import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.NotificationDto;
import com.weltonramos.notification.dto.UserPreferentecesDto;
import com.weltonramos.notification.exception.UserNotFoundException;
import com.weltonramos.notification.repository.UserPreferencesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("v1/notifications")
public class NotificationController {

    private final UserPreferencesRepository repository;

    public NotificationController(UserPreferencesRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/user-preferences")
    public ResponseEntity<Void> createUserPreferences(@RequestBody UserPreferentecesDto userPreferences) {

        //TODO: Mandar mapeamento para service
        UserPreferencesEntity preferences = UserPreferencesEntity.builder()
                .email(userPreferences.getEmail())
                .phoneNumber(userPreferences.getPhoneNumber())
                .optOut(userPreferences.isOptOut())
                .deviceToken(userPreferences.getDeviceToken())
                .createdAt(String.valueOf(new Timestamp(System.currentTimeMillis())))
                .build();

        repository.save(preferences);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping
    public ResponseEntity<Void> sendNotifications(@RequestBody NotificationDto notificationDto) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/user-preferences/{userId}")
    public ResponseEntity<Void> updateOptOut(@PathVariable("userId") String userId) {

        //TODO: Mandar mapeamento para service
        UserPreferencesEntity userPreferencesEntity = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));

        userPreferencesEntity.setOptOut(!userPreferencesEntity.isOptOut());
        userPreferencesEntity.setUpdatedAt(String.valueOf(new Timestamp(System.currentTimeMillis())));
        repository.save(userPreferencesEntity);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
