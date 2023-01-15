package com.example.appointmenthospital.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="appointment", schema = "dentist_system_db")
public class Appointment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "appointment_id")
    private Long id;

    @JsonFormat( pattern = "dd/MM/yyyy")
    @Column(name = "date")
    private LocalDate date;

    @JsonFormat( pattern = "HH:mm")
    @Column(name = "time")
    private LocalTime time;

    //Default value after creation of an appointment is 1 ( Status 0: Visited, Status 1: Not visited)
    @Column(name = "status", columnDefinition = "integer default 1")
    private int status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Appointment(LocalDate date, LocalTime time, Patient patient, Doctor doctor) {
        this.date = date;
        this.time = time;
        this.status = 1; //Default as not visited (Patient has not visited Doctor at creation of the appointment)
        this.patient = patient;
        this.doctor = doctor;
    }

    public Appointment(Long appointmentId, int status) {
        this.id = appointmentId;
        this.status = status;
    }
}
