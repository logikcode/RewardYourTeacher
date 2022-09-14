package com.decagon.rewardyourteacherapi.dto;

import com.decagon.rewardyourteacherapi.Entity.School;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StudentDto {

    private String name;

    private String email;

    private String password;

    private School school;
}
