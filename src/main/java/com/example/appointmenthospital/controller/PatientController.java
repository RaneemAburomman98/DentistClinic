package com.example.appointmenthospital.controller;

import com.example.appointmenthospital.dto.AppointmentDto;
import com.example.appointmenthospital.dto.ReportDto;
import com.example.appointmenthospital.dto.ResultDto;
import com.example.appointmenthospital.model.Appointment;
import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.model.Patient;
import com.example.appointmenthospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    private ResultDto resultDto;

    public void init(){
        resultDto = new ResultDto();
    }


    @GetMapping("/getAllDoctors")
    public List<Doctor> fetchAllDoctor(){
        return patientService.getAllDoctors();
    }

    @GetMapping("/getAllPatients")
    public List<Patient> fetchAllPatient(){
        return patientService.getAllPatients();
    }

    @PutMapping("/update")
    public ResultDto updatePatient(@RequestBody Patient patient){
        init();
        boolean result = patientService.updatePatient(patient);
        if(result){
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Patient updated successfully");
        }else{
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Patient Update Failed");
        }
        return resultDto;
    }

    @PostMapping(path = "/appointment/create", consumes = "application/json", produces = "application/json")
    public ResultDto createAppointment(@RequestBody AppointmentDto appointmentDto){
        init();
        boolean result = patientService.createAppointment(new Appointment(appointmentDto.getDate(),
        		appointmentDto.getTime(),
        		appointmentDto.getPatient(),
        		appointmentDto.getDoctor()));
        if(result){
            resultDto.setResult("Appointment created successfully");
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
        }else{
            resultDto.setResult("Failed Appointment creation");
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
        }
        return resultDto;
    }

    @GetMapping(path = "/appointment/cancel/{appointmentId}", produces = "application/json")
    public ResultDto cancelAppointmentPatient(@PathVariable Long appointmentId){
        init();
        boolean result = patientService.cancelAppointment(appointmentId);
        if(result){
            resultDto.setResult("Appointment Cancel successfully");
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
        }else{
            resultDto.setResult("Failed Appointment Not Cancel");
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
        }
        return resultDto;
    }

    @PostMapping(path = "/generate-report", consumes = "application/json", produces = "application/json")
    public ResultDto generatePatientReport(@RequestBody @Valid ReportDto reportDto){
        init();
        boolean result = patientService.createPatientReport(reportDto);
        if(result){
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Patient Report Created successfully");
        }else{
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Patient report Creation Failed");
        }
        return resultDto;
    }
}
