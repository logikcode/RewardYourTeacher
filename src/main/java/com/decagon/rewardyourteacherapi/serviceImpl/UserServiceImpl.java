package com.decagon.rewardyourteacherapi.serviceImpl;


import com.decagon.rewardyourteacherapi.dto.TeacherResponseDto;
import com.decagon.rewardyourteacherapi.entity.*;
import com.decagon.rewardyourteacherapi.enums.Provider;
import com.decagon.rewardyourteacherapi.exception.UserNotFoundException;
import com.decagon.rewardyourteacherapi.repository.*;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.service.UserService;
import com.decagon.rewardyourteacherapi.OAuth.CustomOAuth2User;
import com.decagon.rewardyourteacherapi.dto.StudentDto;
import com.decagon.rewardyourteacherapi.dto.TeacherDto;
import com.decagon.rewardyourteacherapi.enums.Roles;
import com.decagon.rewardyourteacherapi.exception.EmailAlreadyExistsException;
import com.decagon.rewardyourteacherapi.repository.SubjectsRepository;
import com.decagon.rewardyourteacherapi.repository.TeacherRepository;
import com.decagon.rewardyourteacherapi.repository.UserRepository;
import com.decagon.rewardyourteacherapi.repository.WalletRepository;
import com.decagon.rewardyourteacherapi.security.jwt.JWTTokenProvider;
import com.decagon.rewardyourteacherapi.utils.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final WalletRepository walletRepository;

    private final SubjectsRepository subjectsRepository;

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    private final NotificationRepository notificationRepository;
    private PasswordEncoder passwordEncoder;


    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(WalletRepository walletRepository, UserRepository userRepository, SubjectsRepository subjectsRepository,
                           TeacherRepository teacherRepository, StudentRepository studentRepository,
                           JWTTokenProvider jwtTokenProvider, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.subjectsRepository = subjectsRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.walletRepository = walletRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ResponseAPI<TeacherDto> TeacherSignUp(TeacherDto teacherDto) {

        Optional<Teacher> user = teacherRepository.findTeacherByEmail(teacherDto.getEmail());
        Wallet wallet = new Wallet();

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
            teacher.setProvider(Provider.LOCAL);


            wallet.setUser(teacher);
            wallet.setBalance(BigDecimal.valueOf(0.0));

            walletRepository.save(wallet);
            teacher.setWallet(wallet);


            teacherRepository.save(teacher);


            for(String subjectTitle: teacherDto.getSubjectsList()) {
                subjectsRepository.save(new Subjects(subjectTitle, teacher));
            }

            return new ResponseAPI<>("User Registration successful", LocalDateTime.now(), teacherDto);

        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }

    }

    @Override
    public ResponseAPI<StudentDto> StudentSignUp(StudentDto studentDto) {


        Optional<Student> user = studentRepository.findStudentByEmail(studentDto.getEmail());

        Wallet wallet = new Wallet();

        if (user.isEmpty()) {

            passwordEncoder = new BCryptPasswordEncoder();
            Student student = new Student();

            student.setName(studentDto.getName());
            student.setEmail(studentDto.getEmail());
            student.setPassword(passwordEncoder.encode(studentDto.getPassword()));
            student.setSchool(studentDto.getSchool());
            student.setRole(Roles.STUDENT);
            student.setProvider(Provider.LOCAL);

            wallet.setUser(student);
            wallet.setBalance(BigDecimal.valueOf(0.0));


            walletRepository.save(wallet);
            student.setWallet(wallet);

            studentRepository.save(student);

            walletRepository.save(wallet);
            student.setWallet(wallet);

            student.setProvider(Provider.LOCAL);


            userRepository.save(student);



            return new ResponseAPI<>("User Registration successful", LocalDateTime.now(), studentDto);
        } else {

            throw new EmailAlreadyExistsException("Email Already Exists");
        }
    }

    @Override
    public ResponseAPI<TeacherDto> viewTeacher(long id) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setName(teacher.getName());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setSchool(teacher.getSchool());
        teacherDto.setYearsOfTeaching(teacher.getYearsOfTeaching());
        teacherDto.setSchoolType(teacher.getSchoolType());

        List<String> listOfSubjects = new ArrayList<>();

        for (Subjects sub: teacher.getSubjectsList()) {
            listOfSubjects.add(sub.getTitle());
        }

        teacherDto.setSubjectsList(listOfSubjects);


        return new ResponseAPI<>("success", LocalDateTime.now(), teacherDto);
    }

    @Override
    public ResponseAPI<List<TeacherDto>> searchForTeacher(String name) {

        List<Teacher> teacherList =  teacherRepository.findAllByName(name);

        teacherRepository.findAll();

        List<TeacherDto> teacherDtos = new ArrayList<>();

        for (Teacher teacher: teacherList) {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setName(teacher.getName());
            teacherDto.setEmail(teacher.getEmail());
            teacherDto.setSchool(teacher.getSchool());
            teacherDto.setYearsOfTeaching(teacher.getYearsOfTeaching());
            teacherDto.setSchoolType(teacher.getSchoolType());

            List<String> listOfSubjectList = new ArrayList<>();

            for(Subjects subject: teacher.getSubjectsList()) {
                listOfSubjectList.add(subject.getTitle());
            }
            teacherDto.setSubjectsList(listOfSubjectList);

            teacherDtos.add(teacherDto);
        }

        return new ResponseAPI<>("message", LocalDateTime.now(), teacherDtos);
    }


    public List<TeacherResponseDto> retrieveTeacher(int page, int size) {
        List<Teacher> users;
        Pageable firstPageWithFiveElements = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Teacher> userPage;

        userPage = teacherRepository.findAll(firstPageWithFiveElements);

        users = userPage.getContent();


        return convertTeachersToResponseDto(users);
    }




    public void processOAuthUser(CustomOAuth2User user, Authentication authentication) {
        Optional<User> existUser = userRepository.findUserByEmail(user.getEmail());
        if(existUser.isEmpty()) {
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setProvider(Provider.GOOGLE);
            newUser.setPassword(passwordEncoder.encode(user.getName())); // set user's name as default password
            userRepository.save(newUser);
        }
        String token = jwtTokenProvider.generateToken(authentication);
    }


    public User getTeacherByEmail(String email) {
        Optional<Teacher> user = teacherRepository.findTeacherByEmail(email);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public User getStudentByEmail(String email) {
        Optional<Student> user = studentRepository.findStudentByEmail(email);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }
    @Override
    public ResponseAPI<BigDecimal> userWalletBalance(Long id)  {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BigDecimal walletBallance = user.getWallet().getBalance();

        return new ResponseAPI<>("success", LocalDateTime.now(), walletBallance);
    }



    @Override
    public Page<Notification> retrieveNotifications(Long userId) {
        Pageable pageable = PageRequest.of(0, 5);
        return notificationRepository.findAllByUser_Id(userId, pageable);

    }

    public  List<TeacherResponseDto> retrieveAllTeachersBySchool(String school, int pageNo, int pageSize){

    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("name").ascending());
    Page<Teacher> teachers = teacherRepository.findAllBySchool(school, pageable);

    List<Teacher> teacherList = teachers.getContent();

        return  convertTeachersToResponseDto(teacherList);

    }

    public List<TeacherResponseDto> convertTeachersToResponseDto(List<Teacher> teachers){

        List<TeacherResponseDto> teacherResponseDtoList = new ArrayList<>();

        teachers.forEach(teacher -> {
            TeacherMapper mapper = new TeacherMapper();
            TeacherResponseDto teacherResponseDto =  mapper.teacherEntityToTeacherResponseDtoMapper(teacher);
            teacherResponseDtoList.add(teacherResponseDto);

        });

        return teacherResponseDtoList;

    }

}
