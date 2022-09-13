package com.decagon.rewardyourteacherapi.Entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long uuid;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private Long receiverId;
    private Long senderId;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}
