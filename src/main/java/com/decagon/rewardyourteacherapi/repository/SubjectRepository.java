package com.decagon.rewardyourteacherapi.repository;

import com.decagon.rewardyourteacherapi.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects, Long> {
}
