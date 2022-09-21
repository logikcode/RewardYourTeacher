package com.decagon.rewardyourteacherapi.service;



import com.decagon.rewardyourteacherapi.dto.NotificationDto;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;

public interface NotificationService {

    NotificationDto saveNotification(NotificationDto notificationDto);
    ResponseAPI<NotificationDto> depositNotification(Long transactionId);
   void TransferNotification(Long senderTransactionId, Long receiverTransactionId);

    ResponseAPI<NotificationDto> appreciationNotification(Long senderId, Long receiverId);


}
