package com.decagon.rewardyourteacherapi.entity;

import com.decagon.rewardyourteacherapi.enums.Provider;
import com.decagon.rewardyourteacherapi.enums.Roles;
import com.decagon.rewardyourteacherapi.enums.SchoolType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
public class User extends BaseClass {

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;


    @NotBlank
    @Size(min=8, max = 16)
    @Column(name = "password", nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private int yearsOfTeaching;

    private boolean isRetired;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Message> messageList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Notification> notificationList = new ArrayList<>();


    private String school;

    @Enumerated(EnumType.STRING)
    private SchoolType schoolType;

    @OneToMany(mappedBy = "teacher")
    private List<Subjects> subjectsList;

    @OneToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", provider=" + provider +
                ", transactionList=" + transactionList +
                ", messageList=" + messageList +
                ", notificationList=" + notificationList +
                ", school=" + school +
                ", wallet=" + wallet +
                '}';
    }

    public User(Long id, String name, String email, String password, Roles role, String school) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.school = school;
    }
}
