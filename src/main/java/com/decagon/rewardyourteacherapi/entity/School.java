package com.decagon.rewardyourteacherapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class School extends BaseClass {

    private String schoolName;
    private String schoolType;
    private String schoolAddress;
    private String schoolCity;
    private String schoolState;






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        School school = (School) o;
        return getId() != null && Objects.equals(getId(), school.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
