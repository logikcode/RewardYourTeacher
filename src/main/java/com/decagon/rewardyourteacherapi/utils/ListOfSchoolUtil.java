package com.decagon.rewardyourteacherapi.utils;

import com.decagon.rewardyourteacherapi.dto.SchoolDTO;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
public class ListOfSchoolUtil {

    private static BufferedReader reader;
    private static String line;

    public ListOfSchoolUtil() {

    }


    public static List<SchoolDTO> readAllSchoolsFromCsvFile(String path){
        List<SchoolDTO> schools = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            String[] data;

            while((line = reader.readLine()) != null){
                while((line = reader.readLine()) != null){
                    data = line.split(",");
                   // System.out.println(Arrays.toString(data));
                    SchoolDTO schoolDTO =  new SchoolDTO(data[0] , data[1] , data[2] , data[3], data[4]);
                    schools.add(schoolDTO);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return schools;
    }
}
