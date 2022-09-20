package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.service.TransactionService;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<?> callback(@RequestBody Principal principal) throws Exception {
        return new ResponseEntity<>(transactionService.verifyTransaction(principal, paymentResponse.getData().getReference()), HttpStatus.OK);
    }

}
