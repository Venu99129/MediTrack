package org.airtribe.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.airtribe.exceptions.CSVFilesException;

import java.io.FileReader;
import java.io.FileWriter;

import static org.airtribe.Constants.FilePaths.*;

public class CSVUtil {


   // Returns a CSVWriter for appending patient records to the patient CSV file.
   public static CSVWriter getPatientWriter() throws CSVFilesException {

            try {
                FileWriter fw = new FileWriter(patientFilePath, true);

                return new CSVWriter(fw);
            } catch (java.io.IOException e) {
                throw new CSVFilesException(e.getMessage(), e.getStackTrace());
            }
   }

    // Returns a CSVReader to read patient records from the patient CSV file.
    public static CSVReader getPatientReader() throws CSVFilesException {
        try {
            return new CSVReader(new FileReader(patientFilePath));
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns a CSVWriter for appending doctor records to the doctor CSV file.
    public static CSVWriter getDoctorWriter() throws CSVFilesException {

        try {
            FileWriter fw = new java.io.FileWriter(doctorFilePath, true);

            return new CSVWriter(fw);
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns a CSVWriter for appending appointment records to the appointment CSV file.
    public static CSVWriter getAppointmentWriter() throws CSVFilesException {

        try {
            FileWriter fw = new java.io.FileWriter(appointmentFilePath, true);

             return new CSVWriter(fw);
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns a CSVReader to read doctor records from the doctor CSV file.
    public static CSVReader getDoctorReader() throws CSVFilesException {

        try {
            return new CSVReader(new java.io.FileReader(doctorFilePath));
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns a CSVReader to read appointment records from the appointment CSV file.
    public static CSVReader getAppointmentReader() throws CSVFilesException {

        try {
            return new CSVReader(new java.io.FileReader(appointmentFilePath));
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns a CSVWriter for appending bill records to the bill CSV file.
    public static CSVWriter getBillWriter() throws CSVFilesException {

        try {
           FileWriter fw = new java.io.FileWriter(billFilePath, true);

            return new CSVWriter(fw);
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns a CSVReader to read bill records from the bill CSV file.
    public static CSVReader getBillReader() throws CSVFilesException {

        try {
             return new CSVReader(new java.io.FileReader(billFilePath));
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }



}
