package com.decagon.rewardyourteacherapi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends User{


    private String NIN;
    private String schoolType;
    private int yearsOfTeaching;
    private boolean isRetired;

    @OneToMany(mappedBy = "teacher")
    private List<Subjects> subjectsList;

    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}
