package com.decagon.rewardyourteacherapi.serviceImpl;

import com.decagon.rewardyourteacherapi.dto.SchoolDto;
import com.decagon.rewardyourteacherapi.entity.School;
import com.decagon.rewardyourteacherapi.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;
    public School createSchool(SchoolDto schoolDto){
        School school = new School();
        school.setName(schoolDto.getSchoolName());
        return schoolRepository.save(school);
    }
}
