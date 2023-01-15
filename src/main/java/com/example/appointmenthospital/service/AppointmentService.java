package com.example.appointmenthospital.service;

import com.example.appointmenthospital.dto.ReportDto;
import com.example.appointmenthospital.model.Appointment;
import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.model.Patient;
import com.example.appointmenthospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    public final static int appointmentDuration = 1; //1 Hour

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAvailableAppointments(){
        List<Appointment> appointmentList = appointmentRepository.findAll();
        List<Appointment> availableAppointments = new ArrayList<>();

        //Check if appointment in DB is in dentistAppointment if Yes remove it as available
        appointmentList
                .stream()
                .filter(appointment -> !dentistWorkingTime().containsKey(appointment.getTime().getHour()))
                .forEach(availableAppointments::add);
        return availableAppointments;
    }

    public List<Appointment> getAllBookedAppointments(){
        return appointmentRepository.findAll(); // An appointment is a booked time
    }

    public boolean createAppointment(Appointment appointment){
        Appointment created = appointmentRepository.save(appointment);
        if(created == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkBookedTime(LocalTime time){
        //Check if the time is found in booked appointments.
        boolean booked = getAllBookedAppointments().stream().anyMatch(appointment -> appointment.getTime().equals(time));
        return booked;
    }

    public boolean deleteAppointment(Appointment appointment){
       appointmentRepository.delete(appointment);
       return true;
    }

    public boolean updateAppointment(Appointment appointment){
        Appointment toUpdate = appointmentRepository.findById(appointment.getId()).get();
        toUpdate.setStatus(appointment.getStatus());
        appointmentRepository.save(toUpdate);
        return true;
    }

    public final HashMap<Integer, String> dentistWorkingTime(){
        HashMap<Integer, String> time = new HashMap<>();
        time.put(8, "08:00");
        time.put(9, "09:00");
        time.put(10, "10:00");
        time.put(11, "11:00");
        time.put(12, "12:00");
        time.put(13, "13:00");
        time.put(14, "14:00");
        time.put(15, "15:00");
        time.put(16, "16:00");
        time.put(17, "17:00");

        return time;
    }

    public int totalVisitByPatients(Patient patient, Doctor doctor) {
        long total = appointmentRepository
                .findAll()
                .stream()
                .filter(appointment ->
                        appointment.getPatient().getPatientId().equals(patient.getPatientId())
                                &&
                        appointment.getDoctor().getDoctorId().equals(doctor.getDoctorId()))
                .count();
        return Integer.parseInt(String.valueOf(total));
    }

    public Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).get();
    }

    public List<Appointment> getAllBookedAppointmentsForDoctor(ReportDto reportDto) {
        List<Appointment> doctorsReport = getAllBookedAppointments()
                .stream()
                .filter(appointment -> appointment.getDoctor().getDoctorId().equals(reportDto.getDoctorId())).toList();
        return doctorsReport;
    }

    public List<Appointment> getAllBookedAppointmentsForPatient(ReportDto reportDto) {
        List<Appointment> patientReport = getAllBookedAppointments()
                .stream()
                .filter(appointment -> appointment.getPatient().getPatientId().equals(reportDto.getPatientId())).toList();
        return patientReport;
    }
}
