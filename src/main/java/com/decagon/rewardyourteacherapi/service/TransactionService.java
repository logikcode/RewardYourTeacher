package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;

import java.util.List;

/**
 * @author ifeoluwa on 18/09/2022
 * @project
 */
public interface TransactionService {

    Transaction createTransaction(TransactionDTO transactionDTO);

    List<Transaction> getTransactionHistory(String userEmail);
}
