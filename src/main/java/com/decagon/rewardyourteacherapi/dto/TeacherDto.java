package com.decagon.rewardyourteacherapi.dto;

import com.decagon.rewardyourteacherapi.Entity.School;
import com.decagon.rewardyourteacherapi.Entity.Subjects;
import com.decagon.rewardyourteacherapi.enums.SchoolType;
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

    private School school;

    private int yearsOfTeaching;

    private List<Subjects> subjectsList;

    private SchoolType schoolType;

}
