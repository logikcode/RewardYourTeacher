//package com.decagon.rewardyourteacherapi.serviceImpl;
//
//import com.decagon.rewardyourteacherapi.dto.StudentDto;
//import com.decagon.rewardyourteacherapi.dto.TeacherDto;
//import com.decagon.rewardyourteacherapi.entity.*;
//import com.decagon.rewardyourteacherapi.enums.Roles;
//import com.decagon.rewardyourteacherapi.enums.SchoolType;
//import com.decagon.rewardyourteacherapi.repository.SchoolRepository;
//import com.decagon.rewardyourteacherapi.repository.SubjectRepository;
//import com.decagon.rewardyourteacherapi.repository.UserRepository;
//import com.decagon.rewardyourteacherapi.response.RegisterStudentResponse;
//import com.decagon.rewardyourteacherapi.response.RegisterTeacherResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest
//class UserServiceImplTest {
//
//    @Mock
//    UserRepository userRepository;
//
//    @Mock
//    SchoolRepository schoolRepository;
//
//    @Mock
//    SubjectRepository subjectRepository;
//
//    @InjectMocks
//    UserServiceImpl userServiceImpl;
//
//    private Teacher teacher;
//
//    private Student student;
//
//
//    private List<Subjects> subjectsList = new ArrayList<>();
//
//
//    private LocalDateTime time;
//
//    @BeforeEach
//    void setUp() {
//
//        time = LocalDateTime.of(2022, Month.SEPTEMBER, 22, 7, 2, 20, 3000);
//
//        teacher= new Teacher(1L, "TryGod", "trygodnwakwasi@gmail.com", "1234", Roles.TEACHER, "FGC", 25, SchoolType.SECONDARY, subjectsList);
//
//        student = new Student(2L, "Amanda", "amanda@gmail.com", "1212", Roles.STUDENT, "FGC");
//
//
//
//    }
//
//    @Test
//    void teacherSignUp() {
//        List<String> ListOfSubjects = new ArrayList<>();
//        TeacherDto teacherDto = new TeacherDto("TryGod", "trygodnwakwasi@gmail.com", "1234", "FGC", 25, ListOfSubjects, SchoolType.SECONDARY);
//
//
//        var actual = userServiceImpl.TeacherSignUp(teacherDto);
//        RegisterTeacherResponse teacherResponse = new RegisterTeacherResponse("sucess", time, teacherDto);
//
//        assertEquals(teacherResponse.getTeacherDto().getName(), actual.getTeacherDto().getName());
//        assertEquals(teacherResponse.getTeacherDto().getEmail(), actual.getTeacherDto().getEmail());
//        assertEquals(teacherResponse.getTeacherDto().getPassword(), actual.getTeacherDto().getPassword());
//        assertEquals(teacherResponse.getTeacherDto().getSchool(), actual.getTeacherDto().getSchool());
//        assertEquals(teacherResponse.getTeacherDto().getYearsOfTeaching(), actual.getTeacherDto().getYearsOfTeaching());
//        assertEquals(teacherResponse.getTeacherDto().getSubjectsList(), actual.getTeacherDto().getSubjectsList());
//        assertEquals(teacherResponse.getTeacherDto().getSchoolType(), actual.getTeacherDto().getSchoolType());
//
//
//    }
//
//    @Test
//    void studentSignUp() {
//        StudentDto studentDto = new StudentDto("Amanda", "amanda@gmail.com", "1212", "FGC");
//
//
//        var actual = userServiceImpl.StudentSignUp(studentDto);
//        RegisterStudentResponse studentResponse = new RegisterStudentResponse("succes", time, studentDto);
//
//        assertEquals(studentResponse.getStudentDto().getName(), actual.getStudentDto().getName());
//        assertEquals(studentResponse.getStudentDto().getEmail(), actual.getStudentDto().getEmail());
//        assertEquals(studentResponse.getStudentDto().getPassword(), actual.getStudentDto().getPassword());
//        assertEquals(studentResponse.getStudentDto().getSchool(), actual.getStudentDto().getSchool());
//    }
//
//}