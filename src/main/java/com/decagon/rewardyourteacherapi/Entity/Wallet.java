package com.decagon.rewardyourteacherapi.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long balance;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactionList;
}
