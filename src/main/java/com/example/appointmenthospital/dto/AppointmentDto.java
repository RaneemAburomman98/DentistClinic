package com.example.appointmenthospital.dto;

import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

    private Long appointmentId;

    private LocalDate date;

    private LocalTime time;

    private int status;

    private Patient patient;

    private Doctor doctor;
}
