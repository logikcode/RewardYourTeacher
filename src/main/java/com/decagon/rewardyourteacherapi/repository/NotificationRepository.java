package com.decagon.rewardyourteacherapi.repository;


import com.decagon.rewardyourteacherapi.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

