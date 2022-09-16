package com.decagon.rewardyourteacherapi.service;



import com.decagon.rewardyourteacherapi.dto.NotificationDto;
import com.decagon.rewardyourteacherapi.response.NotificationResponse;

public interface NotificationService {

    NotificationDto saveNotification(NotificationDto notificationDto);
    NotificationResponse depositNotification(Long transactionId);
   void TransferNotification(Long senderTransactionId, Long receiverTransactionId);

    NotificationResponse appreciationNotification(Long senderId, Long receiverId);


}
