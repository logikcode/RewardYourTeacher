package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.security.OAuth.CustomOAuth2User;
import com.decagon.rewardyourteacherapi.serviceImpl.TransactionServiceImpl;
import com.decagon.rewardyourteacherapi.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ifeoluwa on 18/09/2022
 * @project
 */

@RestController
@RequestMapping("/api")
public class TransactionController {

    private TransactionServiceImpl transactionService;
    private UserServiceImpl userService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService, UserServiceImpl userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }



    @GetMapping("/transactions")
    public void getTransactionHistory(Authentication authentication, Model model) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        List<Transaction> userTransaction = transactionService.getTransactionHistory(oAuth2User.getEmail());
        model.addAttribute("userTransaction", userTransaction);
    }

}
