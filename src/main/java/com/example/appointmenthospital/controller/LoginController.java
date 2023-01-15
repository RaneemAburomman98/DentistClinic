package com.example.appointmenthospital.controller;

import com.example.appointmenthospital.dto.LoginRequestDto;
import com.example.appointmenthospital.dto.RegisterUserDto;
import com.example.appointmenthospital.dto.ResultDto;
import com.example.appointmenthospital.model.Doctor;
import com.example.appointmenthospital.model.Patient;
import com.example.appointmenthospital.model.Users;
import com.example.appointmenthospital.service.DoctorService;
import com.example.appointmenthospital.service.PatientService;
import com.example.appointmenthospital.service.UserService;
import com.example.appointmenthospital.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtility tokenUtility;

    private ResultDto resultDto;

    public void init(){
        resultDto = new ResultDto();
    }


    @PostMapping(path ="/login", consumes = "application/json", produces = "application/json")
    public ResultDto login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        init();
        Users u = userService.findUserByUsername(loginRequestDto.getUsername());
        Map<String, Object> mapResult = new HashMap<>();
        if(u == null){
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Invalid Username");
            return resultDto;
        }else{
            if(u.getPassword().equals(loginRequestDto.getPassword())){
                String token = tokenUtility.generateToken(u.getUsername());
                mapResult.put("token", token);
                resultDto.setStatusCode(0);
                resultDto.setStatusDescription("Successful");
                resultDto.setResult(mapResult);
            }else{
                resultDto.setStatusCode(1);
                resultDto.setStatusDescription("Error");
                resultDto.setResult("Invalid Password");
            }
            return resultDto;
        }
    }

    @PostMapping(path ="/register", consumes = "application/json", produces = "application/json")
    public ResultDto register(@RequestBody @Valid RegisterUserDto registerUserDto){
        init();
        if(registerUserDto.getProfile().equalsIgnoreCase("doctor")){
            Users u = new Users(registerUserDto.getUsername(),
            		registerUserDto.getPassword(),
            		registerUserDto.getName(),
            		registerUserDto.getProfile());
            boolean result = userService.createUser(u);
            if(result){
                Doctor doctor = new Doctor(u, registerUserDto.getPhoneNumber(), registerUserDto.getNationalID(), registerUserDto.getSpeciality());
                doctorService.createDoctor(doctor);
            }
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Doctor Created Successfully");
        }else if(registerUserDto.getProfile().equalsIgnoreCase("patient")){
            Users u = new Users(registerUserDto.getUsername(), registerUserDto.getPassword(), registerUserDto.getName(), registerUserDto.getProfile());
            boolean result = userService.createUser(u);
            if(result){
                Patient patient = new Patient(u, registerUserDto.getPhoneNumber(), registerUserDto.getAge(), registerUserDto.getGender());
                patientService.createPatient(patient);
            }
            resultDto.setStatusCode(0);
            resultDto.setStatusDescription("Successful");
            resultDto.setResult("Patient Created Successfully");
        }else{
            resultDto.setStatusCode(1);
            resultDto.setStatusDescription("Error");
            resultDto.setResult("Incorrect User Profile");
        }
        return resultDto;
    }
}
