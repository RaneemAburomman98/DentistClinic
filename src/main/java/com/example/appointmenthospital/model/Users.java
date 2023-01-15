package com.example.appointmenthospital.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name="users", schema = "dentist_system_db")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Username Cannot be Empty")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password Cannot be Empty")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Name Cannot be Empty")
    private String name;

    @Column(name = "profile")
    @NotEmpty(message = "Profile Cannot be Empty. Either (Doctor or Patient)")
    private String profile;

    public Users(String username, String password, String name, String profile) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.profile = profile;
    }
}
