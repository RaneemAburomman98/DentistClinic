package com.example.appointmenthospital.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="patient", schema = "dentist_system_db")
public class Patient{

    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long patientId;

    @Column(name = "username")
    @NotEmpty(message = "Username Cannot be Empty")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password Cannot be Empty")
    private String password;

    @Column(name = "patient_name")
    @NotEmpty(message = "Patient name Cannot be Empty")
    private String patientName;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private Character gender;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    public Patient(Users u, int phoneNumber, int age, Character gender) {
        this.username = u.getUsername();
        this.patientName = u.getName();
        this.password = u.getPassword();

        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
    }
}
