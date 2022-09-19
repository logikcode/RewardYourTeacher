package com.decagon.rewardyourteacherapi.response;

import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class RegisterTeacherResponse {

    private String message;
    private LocalDateTime time;
    private TeacherDto teacherDto;
}
