package com.spring.shortneer.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "admin")
public class Admin {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(nullable = false,unique = true)
    private String username;


   @Column(nullable = false, length = 60)
    private String password;
}