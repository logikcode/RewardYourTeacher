package com.decagon.rewardyourteacherapi.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Messaging {

    private Long id;
    private Teacher teacher;
    private Student student;
    private Notification notification;


}
