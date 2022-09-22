package com.decagon.rewardyourteacherapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class SchoolDTO {

    private String name;
    private String type;
    private String address;
    private String city;
    private String state;


//     "schoolName" : "British International School Lagos",
//             "schoolType" : "secondary",
//             "schoolAddress" : "1, Landbridge Avenue, Oniru Private Estate, Victoria Island, Lagos Nigeria",
//             "schoolCity" : "ikeja",
//             "schoolState" : "Lagos"
}
