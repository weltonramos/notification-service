package com.weltonramos.notification.domain;

public enum NotificationChannel {

    EMAIL(1, "E-mail"),
    SMS(2, "Sms");

    NotificationChannel(int code, String description) {
    }
}
