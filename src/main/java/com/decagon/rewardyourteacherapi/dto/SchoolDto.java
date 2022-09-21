package com.decagon.rewardyourteacherapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class SchoolDto {

    private String schoolName;
    private String schoolType;
    private String schoolAddress;
    private String schoolCity;
    private String schoolState;


//     "schoolName" : "British International School Lagos",
//             "schoolType" : "secondary",
//             "schoolAddress" : "1, Landbridge Avenue, Oniru Private Estate, Victoria Island, Lagos Nigeria",
//             "schoolCity" : "ikeja",
//             "schoolState" : "Lagos"
}
