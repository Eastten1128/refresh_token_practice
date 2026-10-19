package com.example.jdr.notice.entity;

import com.example.jdr.notice.global.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
