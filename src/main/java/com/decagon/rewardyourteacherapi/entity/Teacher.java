package com.decagon.rewardyourteacherapi.entity;

import com.decagon.rewardyourteacherapi.enums.Roles;
import com.decagon.rewardyourteacherapi.enums.SchoolType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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


    public Teacher(Long id, String name, String email, String password, Roles role, String school, int yearsOfTeaching, SchoolType schoolType, List<Subjects> subjectsList) {
        super(id, name, email, password, role, school);
        this.yearsOfTeaching = yearsOfTeaching;
        this.schoolType = schoolType;
        this.subjectsList = subjectsList;
    }
}
