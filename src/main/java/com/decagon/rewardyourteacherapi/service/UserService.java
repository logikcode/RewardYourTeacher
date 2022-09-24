package com.decagon.rewardyourteacherapi.service;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.entity.Teacher;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto);

    ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto);

    ResponseAPI<TeacherDto> viewTeacher(long id);

    ResponseAPI<List<TeacherDto>> searchForTeacher(String name);

    ResponseAPI<Map<String, Object>> retrieveTeacher(Pageable pageable);

    ResponseAPI<BigDecimal> userWalletBalance(Long id);

    Page<Teacher> retrieveAllTeachersBySchoolName(String schoolName, int pageNo, int pageSize);
}
