package com.decagon.rewardyourteacherapi.response;

import com.decagon.rewardyourteacherapi.dto.NotificationDto;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class NotificationResponse {
    private String message;
    private LocalDateTime time;
    private NotificationDto notificationDto;
}
