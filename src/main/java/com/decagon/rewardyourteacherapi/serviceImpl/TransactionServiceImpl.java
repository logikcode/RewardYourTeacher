package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.TransactionDTO;
import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
import com.decagon.rewardyourteacherapi.utils.AuthDetails;
import com.decagon.rewardyourteacherapi.utils.VerifyTransactionResponse;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.client.methods.HttpGet;
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
import java.math.BigDecimal;
import java.security.Principal;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private AuthDetails authDetails;

    @Value("${secret_key}")
    private String PAYSTACK_SECRET_KEY;

    @Value("${paystack_url}")
    private  String PAYSTACK_BASE_URL;

    @Value("${verification_url}")
    private String PAYSTACK_VERIFY_URL;
    private final NotificationServiceImpl notificationService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository,
                                  NotificationServiceImpl notificationService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }


    @Override
    public PaymentResponse initDeposit(Principal principal, PaymentRequest paymentRequest, Long amount) throws Exception {
        User user = authDetails.getAuthorizedUser(principal);

        PaymentResponse paymentResponse;
        paymentRequest.setAmount(amount * 100);
        paymentRequest.setEmail(user.getEmail());

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
    public VerifyTransactionResponse verifyTransaction(Principal principal, String reference) throws Exception {
        System.out.println("called?");
        User user = authDetails.getAuthorizedUser(principal);
        Transaction transaction = new Transaction();
        notificationService.depositNotification(transaction.getId());
        VerifyTransactionResponse paystackresponse = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY_URL + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer PAYSTACK_SECRET_KEY");
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Error Occured while connecting to paystack url");
            }
            ObjectMapper mapper = new ObjectMapper();

            //mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            paystackresponse = mapper.readValue(result.toString(), VerifyTransactionResponse.class);

            if (paystackresponse == null || paystackresponse.getStatus().equals("false")) {
                throw new Exception("An error occurred while  verifying payment");
            } else if (paystackresponse.getData().getStatus().equals("success")) {
                transaction.setAmount(paystackresponse.getData().getAmount().divide(BigDecimal.valueOf(100)));
                transaction.setTransactionType(TransactionType.CREDIT);
                user.getWallet().setBalance(user.getWallet().getBalance().add(transaction.getAmount()));
                transaction.setUser(user);

                transactionRepository.save(transaction);

                notificationService.depositNotification(transaction.getId());
                //PAYMENT IS SUCCESSFUL, APPLY VALUE TO THE TRANSACTION
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Internal server error");
        }
        return paystackresponse;
    }

}

