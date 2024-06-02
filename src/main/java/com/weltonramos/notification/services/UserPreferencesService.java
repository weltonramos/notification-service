package com.weltonramos.notification.services;

import com.weltonramos.notification.domain.UserPreferencesEntity;
import com.weltonramos.notification.dto.UserPreferentecesDto;

public interface UserPreferencesService {

    void createUserPreferences(UserPreferentecesDto userPreferences);

    void updateUserPreferences(String userId, UserPreferentecesDto userPreferences);

    UserPreferencesEntity getUserPreferences(String userId);

}
