package com.decagon.rewardyourteacherapi.service;


import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import com.decagon.rewardyourteacherapi.utils.VerifyTransactionResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.security.Principal;

@Service
public interface TransactionService {


    PaymentResponse initDeposit(Principal principal, PaymentRequest paymentRequest, Long amount) throws Exception;
    VerifyTransactionResponse verifyTransaction(String reference) throws Exception;
//    Transaction createTransaction(TransactionDTO transactionDTO);
    List<Transaction> getTransactionHistory(String userEmail);



}
