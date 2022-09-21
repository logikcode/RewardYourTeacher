package com.decagon.rewardyourteacherapi.controller;

import com.decagon.rewardyourteacherapi.dto.SchoolDto;
import com.decagon.rewardyourteacherapi.entity.School;
import com.decagon.rewardyourteacherapi.serviceImpl.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;
    @GetMapping("teachers/{schoolname}")
    public void registerSchool(@RequestParam("schoolname") String schoolName){

    }
    @PostMapping("school/create")
    public School insertSchool(@RequestBody SchoolDto schoolDto){
       return schoolService.createSchool(schoolDto);
    }

}
