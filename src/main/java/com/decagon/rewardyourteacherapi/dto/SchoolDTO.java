package com.decagon.rewardyourteacherapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolDTO {
    private String name;
    private String type;
    private String address;
    private String city;
    private String state;
}
