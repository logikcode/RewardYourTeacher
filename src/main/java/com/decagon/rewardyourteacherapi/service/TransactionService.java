package com.decagon.rewardyourteacherapi.service;


import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import com.decagon.rewardyourteacherapi.utils.VerifyTransactionResponse;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface TransactionService {


    PaymentResponse initDeposit(Principal principal, PaymentRequest paymentRequest, Long amount) throws Exception;
    public VerifyTransactionResponse verifyTransaction(Principal principal, String reference) throws Exception;


}
