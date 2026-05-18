package org.airtribe.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.airtribe.Constants.FilePaths;
import org.airtribe.entity.Appointment;
import org.airtribe.entity.Doctor;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.util.CSVUtil;

import java.io.IOException;
import java.util.List;

import static org.airtribe.Constants.FilePaths.tempFilePath;


public class DoctorRepository {

    AppointmentRepository appointmentRepository;

    // Initializes the DoctorRepository and its dependent AppointmentRepository.
    public DoctorRepository() {
        appointmentRepository = new AppointmentRepository();
    }

    // Saves a doctor's data to the doctors CSV file.
    public Doctor saveDoctorData(Doctor doctor) throws CSVFilesException {
        try (CSVWriter doctorWriter = CSVUtil.getDoctorWriter()) {

            String[] doctorData = {
                    String.valueOf(doctor.getId()),
                    doctor.getName(),
                    doctor.getEmail(),
                    doctor.getSpecialization()
            };

            doctorWriter.writeNext(doctorData);
            doctorWriter.flush();
            return doctor;
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Deletes a doctor by ID and cancels all their associated appointments.
    public void deleteDoctorDataById(int doctorId) throws CSVFilesException, EntityNotFoundException {
        try (
                CSVReader doctorReader = CSVUtil.getDoctorReader();
                CSVWriter tempWriter = new CSVWriter(new java.io.FileWriter(tempFilePath))
                ) {
            List<String[]> allData = doctorReader.readAll();
            String[] header = allData.getFirst(); // Store header

            tempWriter.writeNext(header); // Write header to temp file

            allData.removeFirst(); // Remove header row if present
            boolean doctorFound = false;
            for (String[] data : allData) {
                if(data.length > 0 && Integer.parseInt(data[0]) == doctorId) {
                    doctorFound = true;
                    continue; // Skip writing this patient's data to the temp file
                }
                if (data.length > 0 && Integer.parseInt(data[0]) != doctorId) {
                    tempWriter.writeNext(data);
                }
            }


            if(!doctorFound) {
                throw new EntityNotFoundException("Doctor with ID " + doctorId + " not found.");
            }

            // Cancel all appointments associated with the deleted doctor
            List<Appointment> doctorAppointments = appointmentRepository.findAppointmentsByDoctorId(doctorId);
            appointmentRepository.cancelAppointments(doctorAppointments);

            tempWriter.flush();

        } catch (IOException | CsvException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
        try {
            java.nio.file.Files.move(java.nio.file.Paths.get(tempFilePath), java.nio.file.Paths.get(FilePaths.doctorFilePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CSVFilesException(
                    e.getMessage(),
                    e.getStackTrace()
            );
        }


    }

    // Finds and returns a doctor by ID from the doctors CSV.
    public Doctor findDoctorById(int doctorID) throws CSVFilesException, EntityNotFoundException {
        try(CSVReader doctorReader = CSVUtil.getDoctorReader()) {
            List<String[]> allData = doctorReader.readAll();
            allData.removeFirst();// Remove header row if present
            for (String[] data : allData) {
                if (data.length > 0 && Integer.parseInt(data[0]) == doctorID) {
                    return new Doctor(Integer.parseInt(data[0]), data[1], data[2], data[3]);
                }
            }
        } catch( Exception e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
        throw new EntityNotFoundException("Doctor with ID " + doctorID + " not found.");
    }

    // Returns a list of all doctors from the doctors CSV.
    public List<Doctor> findAllDoctors() throws CSVFilesException {
        try (CSVReader doctorReader = CSVUtil.getDoctorReader()) {
            List<String[]> allData = doctorReader.readAll();
            allData.removeFirst(); // Remove header row if present

            return allData.stream()
                    .filter(data -> data.length > 0)
                    .map(data -> new Doctor(Integer.parseInt(data[0]), data[1], data[2], data[3]))
                    .toList();
        } catch( Exception e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }
}
