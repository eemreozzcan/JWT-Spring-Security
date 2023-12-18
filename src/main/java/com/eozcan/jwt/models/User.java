package com.eozcan.jwt.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity //JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name = "users") //JPA annotation to specify the table name
@Data //Lombok annotation to generate getters and setters
public class User {
    @Id //JPA annotation to specify the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA annotation to specify the primary key generation strategy
    @Column(nullable = false) //JPA annotation to specify the mapped column for a persistent property or field
    private Long id; //User id

    private String name; //User name
    private String email;   //User email
    private String password; //User password
    private String phone; //User phone


}
