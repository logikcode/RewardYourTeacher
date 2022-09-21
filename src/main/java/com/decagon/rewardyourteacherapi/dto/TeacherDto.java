package com.decagon.rewardyourteacherapi.dto;

import com.decagon.rewardyourteacherapi.enums.SchoolType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherDto {

    private String name;

    private String email;

    private String password;

    private String school;

    private int yearsOfTeaching;

    private List<String> subjectsList;

    private SchoolType schoolType;

}
