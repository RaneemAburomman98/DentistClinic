package com.example.appointmenthospital.service;

import com.example.appointmenthospital.dto.ReportDto;
import com.example.appointmenthospital.model.Appointment;
import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.model.Patient;
import com.example.appointmenthospital.repository.DoctorRepository;
import com.example.appointmenthospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private FileManagementService fileManagementService;

    /**
     * Create Patient
     * @param patient
     * @return
     */
    public boolean createPatient(Patient patient){
        Patient created = patientRepository.save(patient);
        if(created == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Update Patient
     * @param patient
     * @return
     */
    public boolean updatePatient(Patient patient){
        Optional<Patient> fetch = patientRepository.findById(patient.getPatientId());
        if(fetch.isEmpty()){
            return false;
        }else{
            Patient toUpdate = fetch.get();
            toUpdate.setPassword(patient.getPassword());
            toUpdate.setPatientName(patient.getPatientName());
            toUpdate.setPhoneNumber(patient.getPhoneNumber());
            toUpdate.setAge(patient.getAge());
            toUpdate.setGender(patient.getGender());
            patientRepository.saveAndFlush(toUpdate);
            return true;
        }
    }

    /**
     * Get all Doctors
     * @return
     */
    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    /**
     * Get all Patients
     * @return
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Get patient by id
     * @param patientId
     * @return
     */
    public Patient findPatientById(Long patientId) {
        return patientRepository.findById(patientId).get();
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
     * Create patient report
     * @param reportDto
     * @return
     */
    public boolean createPatientReport(ReportDto reportDto){
        List<Appointment> appointmentsForDoctor = appointmentService.getAllBookedAppointmentsForPatient(reportDto);
        return fileManagementService.createCSVReport("patient-report.csv", appointmentsForDoctor);
    }


}
