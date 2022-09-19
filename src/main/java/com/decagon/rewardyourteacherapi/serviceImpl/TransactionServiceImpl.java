package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.repository.TransactionRepository;
import com.decagon.rewardyourteacherapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ifeoluwa on 18/09/2022
 * @project
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private UserServiceImpl userService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserServiceImpl userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }



    @Override
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionType(transactionDTO.getTransactionType());
        newTransaction.setAmount(transactionDTO.getAmount());
        newTransaction.setUser(transactionDTO.getUser());
        transactionRepository.save(newTransaction);
        return newTransaction;
    }

    @Override
    public List<Transaction> getTransactionHistory(String email) {
        User user = userService.getUserByEmail(email);
        List<Transaction> transactionHistory = transactionRepository.findUserTransactionHistory(user.getId());
        return transactionHistory;
    }


}
