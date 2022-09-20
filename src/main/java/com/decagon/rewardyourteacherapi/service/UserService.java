package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.entity.Teacher;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto);

    ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto);

    ResponseAPI<TeacherDto> retrieveTeacher(String role, Pageable pageable, TeacherDto teacherDto);
}
