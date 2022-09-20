package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.entity.*;
import com.decagon.rewardyourteacherapi.enums.Provider;
import com.decagon.rewardyourteacherapi.exception.UserNotFoundException;
import com.decagon.rewardyourteacherapi.repository.SubjectRepository;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.security.JWTTokenProvider;
import com.decagon.rewardyourteacherapi.security.OAuth.CustomOAuth2User;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.enums.Roles;
import com.decagon.rewardyourteacherapi.exception.EmailAlreadyExistsException;
import com.decagon.rewardyourteacherapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final SubjectRepository subjectRepository;

    private PasswordEncoder passwordEncoder;

    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SubjectRepository subjectRepository, JWTTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto) {

        Optional<User> user = userRepository.findUserByEmail(teacherDto.getEmail());

        if (user.isEmpty()) {

            passwordEncoder = new BCryptPasswordEncoder();
            Teacher teacher = new Teacher();

            teacher.setName(teacherDto.getName());
            teacher.setEmail(teacherDto.getEmail());
            teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
            teacher.setSchool(teacherDto.getSchool());
            teacher.setRole(Roles.TEACHER);
            teacher.setYearsOfTeaching(teacherDto.getYearsOfTeaching());
            teacher.setSchoolType(teacherDto.getSchoolType());

            userRepository.save(teacher);


            for(String subjectTitle: teacherDto.getSubjectsList()) {
                subjectRepository.save(new Subjects(subjectTitle, teacher));
            }

            return new ResponseAPI<>("User Registration successful", LocalDateTime.now(), teacherDto);

        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }

    }

    @Override
    public ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto) {

        Optional<User> user = userRepository.findUserByEmail(studentDto.getEmail());

        if (user.isEmpty()) {

            passwordEncoder = new BCryptPasswordEncoder();
            Student student = new Student();

            student.setName(studentDto.getName());
            student.setEmail(studentDto.getEmail());
            student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            student.setSchool(studentDto.getSchool());
            student.setRole(Roles.STUDENT);

            userRepository.save(student);


            return new ResponseAPI<>("User Registration successful", LocalDateTime.now(), studentDto);
        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }
    }

    @Override
    public ResponseAPI<TeacherDto> retrieveTeacher(String role, Pageable pageable, TeacherDto teacherDto) {
        List<User> users;
        Pageable firstPageWithFiveElements = PageRequest.of(0, 5, Sort.by("name").descending());
        Page<User> userPage;
        if (role == null)
            userPage = userRepository.findAll(firstPageWithFiveElements);
        else
            userPage = (Page<User>) userRepository.findAllByRole(role, firstPageWithFiveElements);
        users = userPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("currentPage", userPage.getNumber());
        response.put("totalItems", userPage.getTotalElements());
        response.put("totalPages", userPage.getTotalPages());

        return new ResponseAPI<>("Success", LocalDateTime.now(), teacherDto);
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
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

}
