package org.airtribe.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.airtribe.Constants.FilePaths;
import org.airtribe.entity.Patient;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.util.CSVUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PatientRepository {

    final String tempFilePath = "src/main/resources/temp.csv";
    AppointmentRepository appointmentRepository;

    // Initializes the PatientRepository and its dependent AppointmentRepository.
    public PatientRepository(){
        appointmentRepository = new AppointmentRepository();
    }

    // Saves a patient's data to the patients CSV file.
    public void savePatientData(Patient patient) throws CSVFilesException {

        try(CSVWriter patientWriter = CSVUtil.getPatientWriter()) {
                    String[] patientData = {
                    String.valueOf(patient.getId()),
                    patient.getName(),
                    patient.getEmail(),
                    patient.getMedicalHistory()
            };

            patientWriter.writeNext(patientData);
            patientWriter.flush();
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Deletes a patient by ID and replaces the patients CSV atomically via a temp file.
    public void deletePatientDataById(int patientId)
            throws CSVFilesException, EntityNotFoundException {

        try (
                CSVWriter tempWriter = new CSVWriter(new FileWriter(tempFilePath));

                CSVReader patientReader = CSVUtil.getPatientReader()
        ) {

            List<String[]> allData = patientReader.readAll();

            String[] header = allData.getFirst(); // Store header
            allData.removeFirst(); // remove header

            tempWriter.writeNext(header); // Write header to temp file

            boolean patientFound = false;

            for (String[] data : allData) {

                if (data.length > 0 &&
                        Integer.parseInt(data[0]) == patientId) {

                    patientFound = true;
                    continue;
                }

                tempWriter.writeNext(data);
            }

            if (!patientFound) {
                throw new EntityNotFoundException(
                        "Patient with ID " + patientId + " not found."
                );
            }

            tempWriter.flush();

        } catch (IOException | CsvException e) {

            throw new CSVFilesException(
                    e.getMessage(),
                    e.getStackTrace()
            );
        }

        try {

            Files.move(
                    Paths.get(tempFilePath),
                    Paths.get(FilePaths.patientFilePath),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {

            throw new CSVFilesException(
                    e.getMessage(),
                    e.getStackTrace()
            );
        }
    }

    // Finds and returns a patient by ID from the patients CSV.
    public Patient findPatientById(int patientId) throws CSVFilesException, EntityNotFoundException {
        try {
            CSVReader patientReader = CSVUtil.getPatientReader();
            List<String[]> allData = patientReader.readAll();
            allData.removeFirst(); // Remove header row if present
            for (String[] data : allData) {
                if (data.length > 0 && Integer.parseInt(data[0]) == patientId) {
                    return new Patient(Integer.parseInt(data[0]), data[1], data[2], data[3]);
                }
            }
        } catch( Exception e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
        throw new EntityNotFoundException("Patient with ID " + patientId + " not found.");
    }


    // Returns a list of all patients from the patients CSV.
    public List<Patient> findAllPatients() throws CSVFilesException {
        try {
            CSVReader patientReader = CSVUtil.getPatientReader();
            List<String[]> allData = patientReader.readAll();
            allData.removeFirst(); // Remove header row if present
            List<Patient> patients = new java.util.ArrayList<>();
            for (String[] data : allData) {
                if (data.length > 0) {
                    patients.add(new Patient(Integer.parseInt(data[0]), data[1], data[2], data[3]));
                }
            }
            return patients;
        } catch( Exception e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

}
