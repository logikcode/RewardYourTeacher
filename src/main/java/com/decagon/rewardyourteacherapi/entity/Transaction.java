package com.decagon.rewardyourteacherapi.entity;

import com.decagon.rewardyourteacherapi.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
public class Transaction extends BaseClass {

    private UUID uuid = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Long amount;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId" , referencedColumnName = "id")
    private User user;



}
