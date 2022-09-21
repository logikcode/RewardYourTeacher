package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.dto.UserDTO;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.serviceImpl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    private final MailService mailService;


    @Autowired
    public UserController(UserService userService, MailService mailService) {
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping(value = "/teachers-registration")
    public ResponseEntity<Object> teacherRegistration(@RequestBody TeacherDto teacherDto) throws MessagingException {

        ResponseAPI<TeacherDto> teacherResponse = userService.TeacherSignUp(teacherDto);

        UserDTO userDTO = new UserDTO();
        userDTO.setName(teacherDto.getName());
        userDTO.setEmail(teacherDto.getEmail());

        mailService.sendEmail(userDTO);
        return new ResponseEntity<>(teacherResponse, CREATED);
    }

    @PostMapping(value = "/students-registration")
    public ResponseEntity<Object> studentRegistration(@RequestBody StudentDto studentDto) throws MessagingException {

        ResponseAPI<StudentDto> studentResponse = userService.StudentSignUp(studentDto);

        UserDTO userDTO = new UserDTO();
        userDTO.setName(studentDto.getName());
        userDTO.setEmail(studentDto.getEmail());

        mailService.sendEmail(userDTO);
        return new ResponseEntity<>(studentResponse, CREATED);
    }

    @GetMapping(value = "/retrieve_teacher")
    public ResponseEntity<Object> retrieveTeacher(@PathVariable(value = "role") @RequestParam(required = false) String role,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok().body(userService.retrieveTeacher(Pageable.unpaged()));
    }
}
