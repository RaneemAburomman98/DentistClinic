package com.example.appointmenthospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    //----- Obligatory Information for any User (Doctor or Patient) ----//
    @NotBlank(message = "Username cannot be null or empty")
    private String username;

    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    private int phoneNumber;

    @NotBlank(message = "Profile cannot be null or empty")
    private String profile; // Obligatory to identify the user to be created


    //------- For Doctor Information -------//
    private String nationalID; // Can be null if is a Patient

    private String speciality; // Can be null if is a Patient


    //------- For Patient Information -------//
    private int age; // Can be null if is a Doctor

    private Character gender; // Can be null if is a Doctor
}
