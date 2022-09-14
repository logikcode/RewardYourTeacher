package com.decagon.rewardyourteacherapi.Entity;

import com.decagon.rewardyourteacherapi.enums.Roles;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
public class User extends BaseClass implements Serializable {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sequence",
            initialValue = 1000
    )
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "email", nullable = false, unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;


    @NotBlank
    @Size(min=8, max = 16)
    @Column(name = "password", nullable = false, unique = true)
    private String password;



    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;



    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactionList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Message> messageList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Notification> notificationList = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "school_Id" , referencedColumnName = "id")
    private School school;


}
