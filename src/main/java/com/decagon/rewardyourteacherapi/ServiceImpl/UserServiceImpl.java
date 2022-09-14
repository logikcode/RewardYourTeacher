package com.decagon.rewardyourteacherapi.ServiceImpl;

import com.decagon.rewardyourteacherapi.Entity.Student;
import com.decagon.rewardyourteacherapi.Entity.Teacher;
import com.decagon.rewardyourteacherapi.Entity.User;
import com.decagon.rewardyourteacherapi.Exception.EmailAlreadyExistsException;
import com.decagon.rewardyourteacherapi.Repository.UserRepository;
import com.decagon.rewardyourteacherapi.Service.UserService;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.enums.Roles;
import com.decagon.rewardyourteacherapi.response.RegisterStudentResponse;
import com.decagon.rewardyourteacherapi.response.RegisterTeacherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterTeacherResponse TeacherSignUp(TeacherDto teacherDto) {

        Optional<User> user = userRepository.findUserByEmail(teacherDto.getEmail());

        if (user.isEmpty()) {

            Teacher teacher = new Teacher();

            teacher.setName(teacherDto.getName());
            teacher.setEmail(teacherDto.getEmail());
            teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
            teacher.setSchool(teacherDto.getSchool());
            teacher.setRole(Roles.TEACHER);
            teacher.setYearsOfTeaching(teacherDto.getYearsOfTeaching());
            teacher.setSubjectsList(teacherDto.getSubjectsList());
            teacher.setSchoolType(teacherDto.getSchoolType());

            userRepository.save(teacher);

            return new RegisterTeacherResponse("User Registration successful", LocalDateTime.now(), teacherDto);

        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }

    }

    @Override
    public RegisterStudentResponse StudentSignUp(StudentDto studentDto) {

        Optional<User> user = userRepository.findUserByEmail(studentDto.getEmail());

        if (user.isEmpty()) {

            Student student = new Student();

            student.setName(studentDto.getName());
            student.setEmail(studentDto.getEmail());
            student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            student.setSchool(studentDto.getSchool());

            userRepository.save(student);

            return new RegisterStudentResponse("User Registration successful", LocalDateTime.now(), studentDto);
        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }



    }
}
