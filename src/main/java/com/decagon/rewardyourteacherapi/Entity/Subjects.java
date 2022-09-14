package com.decagon.rewardyourteacherapi.Entity;

import javax.persistence.*;
@Entity
public class Subjects extends BaseClass{

    private String title;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
}
