package com.decagon.rewardyourteacherapi.controller;


import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.response.TransactionResponse;
import com.decagon.rewardyourteacherapi.service.TransactionService;
import com.decagon.rewardyourteacherapi.serviceImpl.TransactionServiceImpl;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class TransactionController {
    private final TransactionServiceImpl transactionService;
    private PaymentResponse paymentResponse = new PaymentResponse();


    @Autowired
    public TransactionController(TransactionServiceImpl transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping(value = "/deposit")
    public ResponseEntity<?> deposit(Principal principal, @RequestParam Long amount, @RequestBody PaymentRequest paymentRequest) throws Exception {

        paymentResponse = transactionService.initDeposit(principal, paymentRequest, amount);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
    @GetMapping(value = "/callback")
    public ResponseEntity<?> payStackResponse() throws Exception {
        return new ResponseEntity<>(transactionService.verifyTransaction(paymentResponse.getData().getReference()), HttpStatus.OK);
    }
    @GetMapping("/transactions")
    public ResponseEntity<TransactionResponse> getTransactionHistory(Authentication authentication, HttpSession session, Model model) {
        String email = (String) session.getAttribute("loggedUserEmail");
        List<Transaction> userTransaction = transactionService.getTransactionHistory(email);
        model.addAttribute("userTransaction", userTransaction);
        return new ResponseEntity<>(new TransactionResponse("success", userTransaction), HttpStatus.OK);
    }

}
