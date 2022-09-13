package com.decagon.rewardyourteacherapi.Entity;

import javax.persistence.*;
@Entity
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;
}
