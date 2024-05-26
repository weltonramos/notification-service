package com.weltonramos.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPreferentecesDto {

    private String email;
    private String phoneNumber;
    private boolean optOut;
    private String deviceToken;

}
