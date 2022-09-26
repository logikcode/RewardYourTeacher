package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.dto.TeacherResponseDto;
import com.decagon.rewardyourteacherapi.dto.UserDto;
import com.decagon.rewardyourteacherapi.entity.Notification;
import com.decagon.rewardyourteacherapi.entity.Teacher;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.serviceImpl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.FOUND;

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

    @PostMapping(value = "/teachers/registration")
    public ResponseEntity<Object> teacherRegistration(@RequestBody TeacherDto teacherDto) throws MessagingException {

        ResponseAPI<TeacherDto> teacherResponse = userService.TeacherSignUp(teacherDto);

        UserDto userDTO = new UserDto();
        userDTO.setName(teacherDto.getName());
        userDTO.setEmail(teacherDto.getEmail());

        mailService.sendEmail(userDTO);
        return new ResponseEntity<>("Registration successful" + "\n" + "welcome " + userDTO.getName(), CREATED);
    }

    @PostMapping(value = "/students/registration")
    public ResponseEntity<Object> studentRegistration(@RequestBody StudentDto studentDto) throws MessagingException {

        ResponseAPI<StudentDto> studentResponse = userService.StudentSignUp(studentDto);

        UserDto userDTO = new UserDto();
        userDTO.setName(studentDto.getName());
        userDTO.setEmail(studentDto.getEmail());

        mailService.sendEmail(userDTO);
        return new ResponseEntity<>("Registration successful" + "\n" + "welcome " + userDTO.getName(), CREATED);
    }

    @GetMapping(value = "/view/teacher/{id}")
    public ResponseEntity<Object> viewParticularTeacher(@PathVariable("id") long id) {
        return new ResponseEntity<>(userService.viewTeacher(id), FOUND);
    }

    @GetMapping(value = "/search/teacher/{name}")
    public ResponseEntity<Object> searchForTeacher(@PathVariable("name") String name) {
        return new ResponseEntity<>(userService.searchForTeacher(name), FOUND);
    }

    @GetMapping(value = "/retrieve/teachers/{page}/{size}")
    public ResponseEntity<Object> retrieveTeacher(
                                                  @PathVariable("page") int page,
                                                  @PathVariable("size") int size) {
        return ResponseEntity.ok().body(userService.retrieveTeacher(page, size));
    }

    @GetMapping(value = "/retrieve/teachers/{schoolName}/{pageNo}/{pageSize}")
    public ResponseEntity<List<TeacherResponseDto>> retrieveAllTeachersInASchool(@PathVariable("schoolName") String school,
                                                               @PathVariable("pageNo") int pageNo,
                                                               @PathVariable("pageSize") int pageSize) {

        List<TeacherResponseDto> teachers = userService.retrieveAllTeachersBySchool(school, pageNo, pageSize);
        return new ResponseEntity<>(teachers, FOUND);

    }

@GetMapping(value = "/retrieve/balance/{id}")
public ResponseEntity<?> currentUserBalance(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(userService.userWalletBalance(id), OK);

}

@GetMapping(value = "/retrieve/notifications/{userId}")
public ResponseEntity<Page<Notification>> retrieveUserNotifications(@PathVariable("userId") Long id){
       Page<Notification> userNotifications = userService.retrieveNotifications(id);
       return new ResponseEntity<>(userNotifications, FOUND);
}

}
