package com.decagon.rewardyourteacherapi.controller;
import com.decagon.rewardyourteacherapi.response.ResponseAPI;
import com.decagon.rewardyourteacherapi.serviceImpl.SchoolServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolServiceImpl schoolService;


//    public ResponseEntity<ResponseAPI<List<School>>> getAllSchools(Pageable pageable){
//        return new ResponseEntity<>(schoolService.getAllSchools(pageable) , OK);
//
//    }

    @GetMapping(value = "/schools")
    public ResponseEntity<ResponseAPI<Map<String, Object>>> getAllSchools(Pageable pageable){
        return new ResponseEntity<>(schoolService.getAllSchools(pageable) , OK);

    }

}
