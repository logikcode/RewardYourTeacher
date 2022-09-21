package com.decagon.rewardyourteacherapi.controller;


import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.security.OAuth.CustomOAuth2User;
import com.decagon.rewardyourteacherapi.service.TransactionService;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class TransactionController {
    private final TransactionService transactionService;
    private PaymentResponse paymentResponse = new PaymentResponse();


    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping(value = "/deposit")
    public ResponseEntity<?> deposit(Principal principal, @RequestParam Long amount, @RequestBody PaymentRequest paymentRequest) throws Exception {

        paymentResponse = transactionService.initDeposit(principal, paymentRequest, amount);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/callback")
    public ResponseEntity<?> callback() throws Exception {
        return new ResponseEntity<>(transactionService.verifyTransaction(paymentResponse.getData().getReference()), HttpStatus.OK);
    }
    @GetMapping("/transactions")
    public void getTransactionHistory(Authentication authentication, Model model) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        List<Transaction> userTransaction = transactionService.getTransactionHistory(oAuth2User.getEmail());
        model.addAttribute("userTransaction", userTransaction);
    }

}
