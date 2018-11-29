package com.starter.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 5, message = "Username should be at least 5 characters")
    private String username;

    @Email
    private String email;

    @Size(min = 5, message = "Password should be at least 6 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles roles;
}
