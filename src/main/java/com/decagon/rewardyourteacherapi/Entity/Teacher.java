package com.decagon.rewardyourteacherapi.Entity;

import com.decagon.rewardyourteacherapi.enums.SchoolType;
import com.decagon.rewardyourteacherapi.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher extends User{

    private int yearsOfTeaching;
    private boolean isRetired;

    @Enumerated(EnumType.STRING)
    private SchoolType schoolType;

    @OneToMany(mappedBy = "teacher")
    private List<Subjects> subjectsList;

    @OneToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;


}
