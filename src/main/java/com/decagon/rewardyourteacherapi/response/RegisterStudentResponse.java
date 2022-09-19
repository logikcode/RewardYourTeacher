package com.decagon.rewardyourteacherapi.response;

import com.decagon.rewardyourteacherapi.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RegisterStudentResponse {

    private String message;
    private LocalDateTime time;
    private StudentDto studentDto;
}
