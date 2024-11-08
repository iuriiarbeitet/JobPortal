package com.dev.jobportal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users_type")
public class UsersType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;

    private String userTypeName;

    @OneToMany(targetEntity = UsersType.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<Users> users;

}
