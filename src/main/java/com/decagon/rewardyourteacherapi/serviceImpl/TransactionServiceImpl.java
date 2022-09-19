package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
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
    private final String PAYSTACK_SECRET_KEY = "sk_test_fd288e8a28663008ada749f007efb38e141ac7c1";
    private final String PAYSTACK_BASE_URL = "https://api.paystack.co/transaction/initialize";
    private final String PAYSTACK_VERIFY_URL = "https://api.paystack.co/transaction/verify/";
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
    public PaymentResponse initDeposit(Long userId, PaymentRequest paymentRequest) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user", "id", userId));
        Transaction transaction = new Transaction();
        paymentRequest.setEmail(user.getEmail());

        PaymentResponse paymentResponse;
        try {
            Gson gson = new Gson();
            StringEntity entity = new StringEntity(gson.toJson(paymentRequest));
            HttpClient httpClient = HttpClientBuilder.create().build();
            String url = "https://api.paystack.co/transaction/initialize";
            HttpPost post = new HttpPost(url);
            post.setEntity(entity);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer sk_test_fd288e8a28663008ada749f007efb38e141ac7c1");
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
            transaction.setTransactionType(TransactionType.CREDIT);
            transaction.setAmount(paymentRequest.getAmount());
            user.getWallet().setBalance(user.getWallet().getBalance() + paymentRequest.getAmount());
            transaction.setUser(user);
            transactionRepository.save(transaction);
            notificationService.depositNotification(transaction.getId());


        } catch (IOException e) {
            throw new RuntimeException("error occurred while initializing transaction");
        }
        return paymentResponse;
    }

}

