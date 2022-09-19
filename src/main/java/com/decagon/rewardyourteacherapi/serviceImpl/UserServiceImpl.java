package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.UserDTO;
import com.decagon.rewardyourteacherapi.entity.Student;
import com.decagon.rewardyourteacherapi.entity.Teacher;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.Provider;
import com.decagon.rewardyourteacherapi.exception.UserNotFoundException;
import com.decagon.rewardyourteacherapi.security.JWTTokenProvider;
import com.decagon.rewardyourteacherapi.security.OAuth.CustomOAuth2User;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.enums.Roles;
import com.decagon.rewardyourteacherapi.exception.EmailAlreadyExistsException;
import com.decagon.rewardyourteacherapi.repository.UserRepository;
import com.decagon.rewardyourteacherapi.response.RegisterStudentResponse;
import com.decagon.rewardyourteacherapi.response.RegisterTeacherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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

            return new RegisterTeacherResponse("com.decagon.rewardyourteacherapi.entity.User Registration successful", LocalDateTime.now(), teacherDto);

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
            student.setRole(Roles.STUDENT);

            userRepository.save(student);

            return new RegisterStudentResponse("com.decagon.rewardyourteacherapi.entity.User Registration successful", LocalDateTime.now(), studentDto);
        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }
    }

    public void processOAuthUser(CustomOAuth2User user, Authentication authentication) {
        Optional<User> existUser = userRepository.findUserByEmail(user.getEmail());
        if(existUser.isEmpty()) {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setProvider(Provider.GOOGLE);
            newUser.setRole(Roles.ADMIN);
            newUser.setPassword(passwordEncoder.encode(user.getName())); // set user's name as default password
            userRepository.save(newUser);
        }
        String token = jwtTokenProvider.generateToken(authentication);
    }


    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user != null) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }


}
