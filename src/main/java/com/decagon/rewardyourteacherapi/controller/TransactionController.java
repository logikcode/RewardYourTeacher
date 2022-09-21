package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.response.TransactionResponse;
import com.decagon.rewardyourteacherapi.serviceImpl.TransactionServiceImpl;
import com.decagon.rewardyourteacherapi.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ifeoluwa on 18/09/2022
 * @project
 */

@RestController
@RequestMapping()
public class TransactionController {

    private TransactionServiceImpl transactionService;
    private UserServiceImpl userService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService, UserServiceImpl userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }



    @GetMapping("/transactions")
    public ResponseEntity<TransactionResponse> getTransactionHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
//        System.out.println("user logged in here: " + user);
        List<Transaction> userTransaction = transactionService.getTransactionHistory(user.getEmail());
        model.addAttribute("userTransaction", userTransaction);
        return new ResponseEntity<>(new TransactionResponse("success", userTransaction), HttpStatus.OK);
    }

}
