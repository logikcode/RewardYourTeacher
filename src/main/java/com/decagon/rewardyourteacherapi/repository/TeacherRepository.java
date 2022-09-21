package com.decagon.rewardyourteacherapi.repository;

import com.decagon.rewardyourteacherapi.entity.Teacher;
import com.decagon.rewardyourteacherapi.entity.User;
import com.decagon.rewardyourteacherapi.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher , Long> {
    List<Teacher> findAllByName(String name);

    Optional<Teacher> findTeacherByEmail(String email);

}
