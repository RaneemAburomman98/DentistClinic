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
@Table(name="doctor", schema = "dentist_system_db")
public class Doctor{

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long doctorId;

    @Column(name = "username")
    @NotEmpty(message = "Username Cannot be Empty")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Password Cannot be Empty")
    private String password;

    @Column(name = "doctor_name")
    @NotEmpty(message = "Doctor name Cannot be Empty")
    private String doctorName;

    @Column(name = "phone_number")
    private int phoneNumber;

    @Column(name = "national_id")
    private String nationalID;

    @Column(name = "speciality")
    private String speciality;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    public Doctor(Users u, int phoneNumber, String nationalID, String speciality) {
        this.username = u.getUsername();
        this.doctorName = u.getName();
        this.password = u.getPassword();

        this.phoneNumber = phoneNumber;
        this.nationalID = nationalID;
        this.speciality = speciality;
    }
}
