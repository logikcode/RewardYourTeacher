package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import com.decagon.rewardyourteacherapi.exception.UserNotFoundException;
import com.decagon.rewardyourteacherapi.repository.TransactionRepository;
import com.decagon.rewardyourteacherapi.repository.UserRepository;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.service.TransactionService;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    @Value("${secret_key}")
    private String PAYSTACK_SECRET_KEY;

    @Value("${paystack_url}")
    private  String PAYSTACK_BASE_URL;
    private final NotificationServiceImpl notificationService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository,
                                  NotificationServiceImpl notificationService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Transaction createTransaction(TransactionDTO transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setUser(transactionDto.getUser());
        return transactionRepository.save(transaction);

    }

    @Override
    public PaymentResponse initDeposit(PaymentRequest paymentRequest) throws Exception {
        PaymentResponse paymentResponse;
        try {
            Gson gson = new Gson();
            StringEntity entity = new StringEntity(gson.toJson(paymentRequest));
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_BASE_URL);
            post.setEntity(entity);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {

                BufferedReader responseReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = responseReader.readLine()) != null) {
                    System.out.println(line);
                    result.append(line);
                }
            } else {
                throw new AuthenticationException("Error Occurred while initializing transaction");
            }
            ObjectMapper mapper = new ObjectMapper();
            paymentResponse = mapper.readValue(result.toString(), PaymentResponse.class);

        } catch (IOException e) {
            throw new RuntimeException("error occurred while initializing transaction");
        }
        return paymentResponse;
    }

}

