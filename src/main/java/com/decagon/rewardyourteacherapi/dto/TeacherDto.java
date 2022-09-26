package com.decagon.rewardyourteacherapi.dto;

import com.decagon.rewardyourteacherapi.enums.SchoolType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherDto {

    private String name;

    private String email;
    @JsonIgnore
    private String password;

    private String school;

    private int yearsOfTeaching;

    private List<String> subjectsList;

    private SchoolType schoolType;

}
