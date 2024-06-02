package com.weltonramos.notification.controllers;

import com.weltonramos.notification.dto.UserPreferentecesDto;
import com.weltonramos.notification.services.UserPreferencesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-preferences")
public class UserPreferencesController {

    private final UserPreferencesService service;

    public UserPreferencesController(UserPreferencesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createUserPreferences(@RequestBody UserPreferentecesDto userPreferences) {
        service.createUserPreferences(userPreferences);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserPreferences(@PathVariable("userId") String userId, @RequestBody UserPreferentecesDto userPreferentecesDto) {
        service.updateUserPreferences(userId, userPreferentecesDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
