package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;

import java.util.List;

public interface UserService {

    ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto);

    ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto);

    ResponseAPI<TeacherDto> viewTeacher(long id);

    ResponseAPI<List<TeacherDto>> searchForTeacher(String name);
}
