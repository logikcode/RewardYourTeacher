package com.decagon.rewardyourteacherapi.Entity;

import com.decagon.rewardyourteacherapi.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction extends BaseClass {

    private String uuid = UUID.randomUUID().toString();

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private User user;



}
