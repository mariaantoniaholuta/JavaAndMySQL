package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    public User() {
    }
    public User(Integer id, String username, String password, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;

    }
}
