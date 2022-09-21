package com.decagon.rewardyourteacherapi.entity;

import com.decagon.rewardyourteacherapi.enums.Roles;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Student extends User {


    public Student(Long id, String name, String email, String password, Roles role, String school) {
        super(id, name, email, password, role, school);
    }
}
