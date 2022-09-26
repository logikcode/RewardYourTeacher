package com.decagon.rewardyourteacherapi.utils;

import com.decagon.rewardyourteacherapi.dto.TeacherResponseDto;
import com.decagon.rewardyourteacherapi.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherMapper {


    public TeacherResponseDto teacherEntityToTeacherResponseDtoMapper(Teacher teacher) {
        TeacherResponseDto teacherResponseDto = new TeacherResponseDto();
        teacherResponseDto.setName(teacher.getName());
        teacherResponseDto.setEmail(teacher.getEmail());
        teacherResponseDto.setSchool(teacher.getSchool());
        teacherResponseDto.setYearsOfExperience(teacherResponseDto.getYearsOfExperience());
        return teacherResponseDto;
    }




}
