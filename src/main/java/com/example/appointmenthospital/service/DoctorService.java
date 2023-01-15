package com.example.appointmenthospital.service;

import com.example.appointmenthospital.dto.AppointmentDto;
import com.example.appointmenthospital.dto.ReportDto;
import com.example.appointmenthospital.model.Appointment;
import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.model.Patient;
import com.example.appointmenthospital.repository.DoctorRepository;
import com.example.appointmenthospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private FileManagementService fileManagementService;

    /**
     * Create Doctor
     * @param doctor
     * @return
     */
    public boolean createDoctor(Doctor doctor){
        Doctor created = doctorRepository.save(doctor);
        if(created == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Update Doctor
     * @param doctor
     * @return
     */
    public boolean updateDoctor(Doctor doctor){
        Optional<Doctor> fetch = doctorRepository.findById(doctor.getDoctorId());
        if(fetch.isEmpty()){
            return false;
        }else{
            doctorRepository.saveAndFlush(doctor);
            return true;
        }
    }

    /**
     *  All Available Time
     * @return
     */
    public List<LocalTime> getAllAvailableAppointmentTime(){
        return appointmentService.getAllAvailableAppointments().stream().map(Appointment::getTime).collect(Collectors.toList());
    }

    /**
     * All booked timeline
     * @return
     */
    public List<Appointment> getAllBookedAppointments(){
        return appointmentService.getAllBookedAppointments();
    }

    /**
     * Check if time of appointment is booked already if book return false and cancel appointment if true book appointment.
     * @param appointment
     * @return
     */
    public boolean createAppointment(Appointment appointment){
        boolean checkBookTime = appointmentService.checkBookedTime(appointment.getTime());
        if(checkBookTime){
            return false; // Time of appointment already booked
        }else{
            return appointmentService.createAppointment(appointment);
        }
    }

    /**
     * Cancel Appointment
     */
    public boolean cancelAppointment(Long appointmentId){
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        return appointmentService.deleteAppointment(appointment);
    }

    /**
     * Update Appointment status
     */
    public boolean updateAppointmentStatus(Appointment appointment){
        return appointmentService.updateAppointment(appointment);
    }

    /**
     * Create Doctor Report for booked timeline
     */
    public boolean createDoctorReport(ReportDto reportDto){
        List<Appointment> appointmentsForDoctor = appointmentService.getAllBookedAppointmentsForDoctor(reportDto);
        return fileManagementService.createCSVReport("doctor-report.csv", appointmentsForDoctor);
    }

    /**
     * Count total visit by patient to a doctor
     * @param appointmentDto
     * @return
     */
    public int totalPatientVisitedDoctor(AppointmentDto appointmentDto){
        return appointmentService.totalVisitByPatients(appointmentDto.getPatient(), appointmentDto.getDoctor());
    }

    /**
     * View all patients
     * @return
     */
    public List<Patient> getAllPatients(){
        return patientService.getAllPatients();
    }

    /**
     * Get patient information by his id
     * @param patientId
     * @return
     */
    public Patient getPatient(Long patientId){
        return patientService.findPatientById(patientId);
    }


}
