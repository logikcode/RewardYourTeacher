package com.decagon.rewardyourteacherapi.service;


import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    //create a transaction service
    Transaction createTransaction(TransactionDTO transactionDto);

    PaymentResponse initDeposit(PaymentRequest paymentRequest) throws Exception;


}
