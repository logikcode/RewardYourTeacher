package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.service.TransactionService;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @PostMapping(value = "/{id}deposit")
    public ResponseEntity<?> deposit(@RequestParam Long amount, @PathVariable Long id) throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(amount);
        return new ResponseEntity<>(transactionService.initDeposit(id, paymentRequest), HttpStatus.OK);
    }
}
