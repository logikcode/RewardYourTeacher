package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.entity.Transaction;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.entity.Wallet;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
import com.decagon.rewardyourteacherapi.exception.ResourceNotFound;
import com.decagon.rewardyourteacherapi.repository.TransactionRepository;
import com.decagon.rewardyourteacherapi.repository.WalletRepository;
import com.decagon.rewardyourteacherapi.response.PaymentResponse;
import com.decagon.rewardyourteacherapi.service.TransactionService;
import com.decagon.rewardyourteacherapi.utils.AuthDetails;
import com.decagon.rewardyourteacherapi.utils.PaymentRequest;
import com.decagon.rewardyourteacherapi.utils.VerifyTransactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    private UserServiceImpl userService;
    private AuthDetails authDetails;
    private User user = new User();

    @Value("${secret_key}")
    private String PAYSTACK_SECRET_KEY;

    @Value("${paystack_url}")
    private  String PAYSTACK_BASE_URL;

    @Value("${verification_url}")
    private String PAYSTACK_VERIFY_URL;
    private final NotificationServiceImpl notificationService;
    private final WalletRepository walletRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserServiceImpl userService,
                                  NotificationServiceImpl notificationService, AuthDetails authDetails, WalletRepository walletRepository) {
        this.authDetails = authDetails;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }


    @Override
    public PaymentResponse initDeposit(Principal principal, PaymentRequest paymentRequest, Long amount) throws Exception {

        user = (User) authDetails.getAuthorizedUser(principal);

        PaymentResponse paymentResponse;

        paymentRequest.setAmount(amount * 100);
        paymentRequest.setEmail(authDetails.getAuthorizedUser(principal).getEmail());

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
    @Override
    public VerifyTransactionResponse verifyTransaction(String reference) {

        Transaction transaction = new Transaction();
        VerifyTransactionResponse payStackResponse = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY_URL + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new Exception("Error Occurred while connecting to PayStack URL");
            }
            ObjectMapper mapper = new ObjectMapper();


            payStackResponse = mapper.readValue(result.toString(), VerifyTransactionResponse.class);

            if (payStackResponse == null || payStackResponse.getStatus().equals("false")) {
                throw new Exception("An error occurred while  verifying payment");
            } else if (payStackResponse.getData().getStatus().equals("success")) {

                transaction.setAmount(payStackResponse.getData().getAmount().divide(BigDecimal.valueOf(100)));
                transaction.setTransactionType(TransactionType.CREDIT);

                Optional<Wallet> wallet = walletRepository.findById(user.getWallet().getId());
                if (wallet.isPresent()){
                    wallet.get().setBalance(wallet.get().getBalance().add(transaction.getAmount()));
                    walletRepository.save(wallet.get());
                }else
                    throw new ResourceNotFound("wallet", "id", wallet.get().getId());

                transaction.setUser(user);

                transactionRepository.save(transaction);

                notificationService.depositNotification(transaction.getId());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return payStackResponse;
    }
    @Override
    public List<Transaction> getTransactionHistory(String email) {
        User user = userService.getUserByEmail(email);
        List<Transaction> transactionHistory = transactionRepository.findUserTransactionHistory(user.getId());
        return transactionHistory;
    }

}



