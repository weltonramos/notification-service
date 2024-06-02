package com.weltonramos.notification.services;

import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.UserPreferentecesDto;
import com.weltonramos.notification.exception.UserNotFoundException;
import com.weltonramos.notification.repositories.UserPreferencesRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {

    private final UserPreferencesRepository repository;

    public UserPreferencesServiceImpl(UserPreferencesRepository repository) {
        this.repository = repository;
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
    public void updateUserPreferences(String userId, UserPreferentecesDto userPreferences) {

        UserPreferencesEntity userPreferencesEntity = getUserPreferences(userId);

        userPreferencesEntity.setEmail(userPreferences.getEmail());
        userPreferencesEntity.setPhoneNumber(userPreferences.getPhoneNumber());
        userPreferencesEntity.setOptOut(userPreferences.isOptOut());
        userPreferencesEntity.setUpdatedAt(Instant.now());

        repository.save(userPreferencesEntity);
    }

    public UserPreferencesEntity getUserPreferences(String userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User " + userId + " not found."));
    }
}
