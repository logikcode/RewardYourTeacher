package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.NotificationDto;
import com.decagon.rewardyourteacherapi.entity.Notification;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
import com.decagon.rewardyourteacherapi.exception.ResourceNotFound;
import com.decagon.rewardyourteacherapi.repository.NotificationRepository;
import com.decagon.rewardyourteacherapi.repository.TransactionRepository;
import com.decagon.rewardyourteacherapi.repository.UserRepository;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final TransactionRepository transactionRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, ModelMapper mapper, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.notificationRepository = notificationRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public NotificationDto saveNotification(NotificationDto notificationDto) {
        //convert notificationDto to notification
        Notification notification = convertToEntity(notificationDto);

        //save notification
        Notification savedNotification = notificationRepository.save(notification);

        //convert savedNotification to notificationDto and return it

        return convertToDto(savedNotification);
    }

    @Override
    public ResponseAPI<NotificationDto> depositNotification(Long transactionId) {
        Optional<Transaction> tgTransaction = transactionRepository.findById(transactionId);
        NotificationDto notificationDto = new NotificationDto();

        if (tgTransaction.isPresent() && (ObjectUtils.containsConstant(TransactionType.values(), "CREDIT"))) {
            notificationDto.setMessageBody("You have funded your wallet with N" + tgTransaction.get().getAmount());
            notificationDto.setCreateDate(LocalDateTime.now());
            notificationDto.setUser(tgTransaction.get().getUser());
            saveNotification(notificationDto);

        }else{
            throw new ResourceNotFound("tgTransaction", "transactionId", transactionId);
        }

        return new ResponseAPI<>("Notification sent successfully", LocalDateTime.now(), notificationDto);
    }

    @Override
    public void TransferNotification(Long senderTransactionId, Long receiverTransactionId) {
        NotificationDto receiverNotificationDto = receiverNotification(senderTransactionId);
        NotificationDto senderNotificationDto = senderNotification(receiverTransactionId);
        receiverNotificationDto.setMessageBody(receiverNotificationDto.getMessageBody() + senderNotificationDto.getUser().getName());
        senderNotificationDto.setMessageBody(senderNotificationDto.getMessageBody() + receiverNotificationDto.getUser().getName());
        saveNotification(receiverNotificationDto);
        saveNotification(senderNotificationDto);
    }

    @Override
    public ResponseAPI<NotificationDto> appreciationNotification(Long senderId, Long receiverId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);
        NotificationDto notificationDto = new NotificationDto();
        if (sender.isPresent()) {
            notificationDto.setMessageBody(sender.get().getName() +" appreciated you");
            notificationDto.setCreateDate(LocalDateTime.now());
            notificationDto.setUser(receiver.orElseThrow(() -> new ResourceNotFound("receiver", "receiverId", receiverId)));
            saveNotification(notificationDto);
        }else{
            throw new ResourceNotFound("sender", "senderId", senderId);
        }
        return new ResponseAPI<>("Notification sent successfully", LocalDateTime.now(), notificationDto);
    }


    private NotificationDto senderNotification(Long senderTransactionId) {
        Optional<Transaction> senderTransaction = transactionRepository.findById(senderTransactionId);
        NotificationDto notificationDto = new NotificationDto();

        if (senderTransaction.isPresent() && (ObjectUtils.containsConstant(TransactionType.values(), "DEBIT"))) {
            notificationDto.setMessageBody("You have sent N" + senderTransaction.get().getAmount() + " to ");
            notificationDto.setCreateDate(LocalDateTime.now());
            notificationDto.setUser(senderTransaction.get().getUser());
        }else{
            throw new ResourceNotFound("senderTransaction", "senderTransactionId", senderTransactionId);
        }

        return notificationDto;
    }

    private NotificationDto receiverNotification(Long receiverTransactionId) {
        Optional<Transaction> receiverTransaction = transactionRepository.findById(receiverTransactionId);
        NotificationDto notificationDto = new NotificationDto();

        if (receiverTransaction.isPresent() && (ObjectUtils.containsConstant(TransactionType.values(), "CREDIT"))) {
            notificationDto.setMessageBody("You have received N" + receiverTransaction.get().getAmount() + " from ");
            notificationDto.setCreateDate(LocalDateTime.now());
            notificationDto.setUser(receiverTransaction.get().getUser());
        }else{
            throw new ResourceNotFound("receiverTransaction", "receiverTransactionId", receiverTransactionId);
        }
        return notificationDto;
    }


    public NotificationDto convertToDto(Notification notification) {
        return mapper.map(notification, NotificationDto.class);
    }

    public Notification convertToEntity(NotificationDto notificationDto) {
        return mapper.map(notificationDto, Notification.class);
    }
}
