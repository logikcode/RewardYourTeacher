package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import org.springframework.data.domain.Pageable;

import java.util.Map;

import java.util.List;

public interface UserService {

    ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto);

    ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto);


    ResponseAPI<TeacherDto> viewTeacher(long id);

    ResponseAPI<List<TeacherDto>> searchForTeacher(String name);

    ResponseAPI<Map<String, Object>> retrieveTeacher(Pageable pageable);

}
