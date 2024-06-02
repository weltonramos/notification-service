package com.weltonramos.notification.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("user_preferences")
public class UserPreferencesEntity {

    @Id
    private String userId;
    private String email;
    private String phoneNumber;
    private boolean optOut;
    private String deviceToken;
    private Instant createdAt;
    private Instant updatedAt;

    public boolean isOptOutSigned() {
        return this.optOut;
    }

}
