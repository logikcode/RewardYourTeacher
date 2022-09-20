package com.decagon.rewardyourteacherapi.repository;


import com.decagon.rewardyourteacherapi.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query("from Teacher")
    List<User> findAllByRole(String role, Pageable pageable);
}
