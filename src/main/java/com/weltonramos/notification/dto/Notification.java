package com.weltonramos.notification.dto;

import com.weltonramos.notification.domain.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification {

    private String userId;
    private String subject;
    private String message;
    private Set<NotificationChannel> notificationChannels;

}
