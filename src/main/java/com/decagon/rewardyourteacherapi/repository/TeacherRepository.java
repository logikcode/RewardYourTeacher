package com.decagon.rewardyourteacherapi.repository;

import com.decagon.rewardyourteacherapi.entity.Teacher;
import com.decagon.rewardyourteacherapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
