package com.decagon.rewardyourteacherapi.service;


import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import org.springframework.context.PayloadApplicationEvent;

public interface TransactionService {

    //create a transaction service
    Transaction createTransaction(TransactionDTO transactionDto);

    PaymentResponse initDeposit(Long userId, PaymentRequest paymentRequest) throws Exception;


}
