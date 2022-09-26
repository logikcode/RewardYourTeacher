package com.decagon.rewardyourteacherapi.repository;



import com.decagon.rewardyourteacherapi.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByUser_Id(Long id, Pageable pageable);
    Page<Notification> findNotificationByUser(Long id, Pageable pageable);


}

