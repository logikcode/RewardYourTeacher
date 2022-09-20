package com.decagon.rewardyourteacherapi.dto;

import com.decagon.rewardyourteacherapi.entity.School;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private School school;
}
