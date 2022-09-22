package com.decagon.rewardyourteacherapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schools")
public class School extends BaseClass {
   private String name;
    private String type;
    private String address;
    private String city;
    private String state;

//    public School(String name, String type, String address, String city, String state) {
//        this.name = name;
//        this.type = type;
//        this.address = address;
//        this.city = city;
//        this.state = state;
//    }






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
