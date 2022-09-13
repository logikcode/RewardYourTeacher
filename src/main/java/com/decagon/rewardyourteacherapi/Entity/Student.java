package com.decagon.rewardyourteacherapi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends User {

    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;


    @OneToOne
    @JoinColumn(name = "school_id")
    private School school;
}
