package com.decagon.rewardyourteacherapi.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class BaseClass {

    @Id
    private String id = UUID.randomUUID().toString();

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;


}
