package com.example.appointmenthospital.service;

import com.example.appointmenthospital.model.Appointment;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class FileManagementService {


    /**
     * Create Report in CSV for Doctor
     * @param filename
     */
    public boolean createCSVReport(String filename, List<Appointment> appointments) {
        File file = new File(filename);
        try {
            FileWriter outputFile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputFile);

            // adding header to csv
            String[] header = { "From", "To", "Appointment ID" };
            writer.writeNext(header);

            for (int i = 0; i < appointments.size(); i++) {
                String[] data = { appointments.get(i).getDate().toString(), appointments.get(i).getTime().toString(), String.valueOf(appointments.get(i).getId()) };
                writer.writeNext(data);
            }

            // closing writer connection
            writer.close();
            return true;
        }
        catch (Exception e) {
            System.out.println("Report not created");
            return false;
        }
    }
}
