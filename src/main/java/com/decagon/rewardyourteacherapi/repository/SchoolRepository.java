package com.decagon.rewardyourteacherapi.repository;

import com.decagon.rewardyourteacherapi.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository  extends JpaRepository<School, Long> {

}
