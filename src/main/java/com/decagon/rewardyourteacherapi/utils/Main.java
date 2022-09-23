package com.decagon.rewardyourteacherapi.utils;

import com.decagon.rewardyourteacherapi.dto.SchoolDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.decagon.rewardyourteacherapi.utils.ListOfSchoolUtil.readAllSchoolsFromCsvFile;


@Slf4j
public class Main {
    public static void main(String[] args) {

        List<SchoolDto> schools = readAllSchoolsFromCsvFile("src/main/resources/ListOfSchools.csv");

        schools.forEach((school)-> {
            //  System.out.println("Name of school : "+ school + "Address: {}" + address);
            log.info("Name of school : {}  Type: {} Address: {} City: {}  State: {} " , school.getName(), school.getType(), school.getAddress(), school.getCity(), school.getState());
        });
    }
}

