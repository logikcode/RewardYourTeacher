package com.decagon.rewardyourteacherapi.Controller;

import com.decagon.rewardyourteacherapi.Service.UserService;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/teachers-registration")
    public ResponseEntity<Object> teacherRegistration(@RequestBody TeacherDto teacherDto) {
        return new ResponseEntity<>(userService.TeacherSignUp(teacherDto), CREATED);
    }

    @PostMapping(value = "/students-registration")
    public ResponseEntity<Object> studentRegistration(@RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(userService.StudentSignUp(studentDto), CREATED);
    }
}
