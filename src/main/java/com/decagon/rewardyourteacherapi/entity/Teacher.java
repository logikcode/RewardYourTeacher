package com.decagon.rewardyourteacherapi.entity;

import com.decagon.rewardyourteacherapi.enums.SchoolType;
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




}
