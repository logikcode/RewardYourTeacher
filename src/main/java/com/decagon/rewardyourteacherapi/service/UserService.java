package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;

public interface UserService {

    ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto);

    ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto);
}
