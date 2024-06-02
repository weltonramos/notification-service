package com.weltonramos.notification.repositories;

import com.weltonramos.notification.domain.UserPreferencesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesRepository extends MongoRepository<UserPreferencesEntity, String> {
}
