package com.example.appointmenthospital.controller;

import com.example.appointmenthospital.dto.AppointmentDto;
import com.example.appointmenthospital.dto.ReportDto;
import com.example.appointmenthospital.dto.ResultDto;
import com.example.appointmenthospital.model.Appointment;
import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private ResultDto resultDto;

    public void init(){
        resultDto = new ResultDto();
    }


    @PutMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResultDto updateDoctor(@RequestBody Doctor doctor){
        init();
        boolean result = doctorService.updateDoctor(doctor);
        if(result){
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Doctor updated successfully");
        }else{
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Doctor Update Failed");
        }
        return resultDto;
    }

    @GetMapping(path = "/getAllAvailableTime", produces = "application/json")
    public ResultDto getAllAvailableTime(){
        init();
        resultDto.setResult(doctorService.getAllAvailableAppointmentTime());
        resultDto.setStatusCode(0);
        resultDto.setStatusDescription("Successful");
        return resultDto;
    }

    @GetMapping(path = "/getAllBookedTime", produces = "application/json")
    public ResultDto getBookedTimeline(){
        init();
        resultDto.setResult(doctorService.getAllBookedAppointments());
        resultDto.setStatusCode(0);
        resultDto.setStatusDescription("Successful");
        return resultDto;
    }

    @PostMapping(path = "/appointment/create", consumes = "application/json", produces = "application/json")
    public ResultDto createAppointment(@RequestBody AppointmentDto appointmentDto){
        init();
        boolean result = doctorService.createAppointment(new Appointment(appointmentDto.getDate(), appointmentDto.getTime(), appointmentDto.getPatient(), appointmentDto.getDoctor()));
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
    public ResultDto cancelAppointment(@PathVariable Long appointmentId){
        init();
        boolean result = doctorService.cancelAppointment(appointmentId);
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

    @PutMapping(path = "/appointment/update", consumes = "application/json", produces = "application/json")
    public ResultDto updateAppointment(@RequestBody AppointmentDto appointmentDto){
        init();
        boolean result = doctorService.updateAppointmentStatus(new Appointment(appointmentDto.getAppointmentId(), appointmentDto.getStatus()));
        if(result){
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Appointment updated successfully");
        }else{
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Appointment Update Failed");
        }
        return resultDto;
    }

    @GetMapping(path = "/getTotalVisitByPatient", consumes = "application/json", produces = "application/json")
    public ResultDto getTotalPatientVisitCount(@RequestBody AppointmentDto appointmentDto){
        init();
        resultDto.setResult("Total visit by Patient: " + doctorService.totalPatientVisitedDoctor(appointmentDto));
        resultDto.setStatusCode(0);
        resultDto.setStatusDescription("Successful");
        return resultDto;
    }


    @GetMapping(path = "/getTotalPatients", produces = "application/json")
    public ResultDto getAllPatients(){
        init();
        resultDto.setResult(doctorService.getAllPatients());
        resultDto.setStatusCode(0);
        resultDto.setStatusDescription("Successful");
        return resultDto;
    }

    @GetMapping(path = "/getPatient/{patientId}", produces = "application/json")
    public ResultDto getPatientFromId(@PathVariable Long patientId){
        init();
        resultDto.setResult(doctorService.getPatient(patientId));
        resultDto.setStatusCode(0);
        resultDto.setStatusDescription("Successful");
        return resultDto;
    }

    @PostMapping(path = "/generate-report", consumes = "application/json", produces = "application/json")
    public ResultDto generateDoctorReport(@RequestBody @Valid ReportDto reportDto){
        init();
        boolean result = doctorService.createDoctorReport(reportDto);
        if(result){
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Doctor Report Created successfully");
        }else{
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Doctor report Creation Failed");
        }
        return resultDto;
    }
}
