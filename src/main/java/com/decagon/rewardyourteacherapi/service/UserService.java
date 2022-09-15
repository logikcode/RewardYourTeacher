package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.response.RegisterStudentResponse;
import com.decagon.rewardyourteacherapi.response.RegisterTeacherResponse;

public interface UserService {

    RegisterTeacherResponse TeacherSignUp(TeacherDto teacherDto);

    RegisterStudentResponse StudentSignUp(StudentDto studentDto);
}
