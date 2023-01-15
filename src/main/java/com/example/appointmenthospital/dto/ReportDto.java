package com.example.appointmenthospital.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class ReportDto {

    @NotBlank(message = "From Date cannot be null or empty")
    private LocalDate fromDate;

    @NotBlank(message = "To Date cannot be null or empty")
    private LocalDate toDate;

    private Long doctorId;
    private Long patientId;
}
