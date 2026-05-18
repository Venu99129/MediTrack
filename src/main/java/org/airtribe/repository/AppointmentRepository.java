package org.airtribe.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.airtribe.Constants.FilePaths;
import org.airtribe.entity.Appointment;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.util.CSVUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AppointmentRepository {


    // Appends a new appointment record to the appointments CSV file.
    public Appointment createAppointment(Appointment appointment) throws CSVFilesException {
        try (CSVWriter appointmentWriter = CSVUtil.getAppointmentWriter()) {
            String[] appointmentData = {
                    String.valueOf(appointment.getId()),
                    String.valueOf(appointment.getPatientId()),
                    String.valueOf(appointment.getDoctorId()),
                    appointment.getDateTime(),
                    appointment.getStatus()
            };
            appointmentWriter.writeNext(appointmentData);
        } catch (java.io.IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }

        return appointment;
    }

    // Updates the status of an existing appointment and rewrites the CSV atomically via a temporary file.
    public Appointment updateAppointmentStatus(int appointmentId, String newStatus) throws CSVFilesException, EntityNotFoundException {

        Appointment updatedAppointment = null;
        try (CSVReader appointmentReader = CSVUtil.getAppointmentReader();
             CSVWriter tempWriter = new CSVWriter(new FileWriter(FilePaths.tempFilePath))) {

            List<String[]> appointments = appointmentReader.readAll();
            String[] header = appointments.getFirst(); // Store header
            appointments.removeFirst(); // Remove header row if present

            boolean appointmentFound = false;
            Appointment appointmentToUpdate = null;
            for (String[] appointmentData : appointments) {
                if (appointmentData.length > 0 && Integer.parseInt(appointmentData[0]) == appointmentId) {

                    appointmentFound = true;
                    appointmentToUpdate = Appointment.builder()
                            .setId(Integer.parseInt(appointmentData[0]))
                            .setPatientId(Integer.parseInt(appointmentData[1]))
                            .setDoctorId(Integer.parseInt(appointmentData[2]))
                            .setDateTime(appointmentData[3])
                            .build();

                    if (newStatus.equalsIgnoreCase("COMPLETED")) {
                        appointmentToUpdate.completeAppointment();
                    } else if (newStatus.equalsIgnoreCase("CANCELED")) {
                        appointmentToUpdate.cancelAppointment();
                    } else {
                        throw new IllegalArgumentException("Invalid status: " + newStatus);
                    }

                    appointmentData[4] = newStatus; // Update the status
                }
            }
            if (appointmentFound) {
                appointments.addFirst(header); // Add header back if needed

                tempWriter.writeAll(appointments); // Write the updated list back to the CSV
                tempWriter.flush();

                updatedAppointment = appointmentToUpdate;
            }
            else
                throw new EntityNotFoundException("Appointment with ID " + appointmentId + " not found.");

        } catch (CsvException | IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }

        try {
            Files.move(Paths.get(FilePaths.tempFilePath), Paths.get(FilePaths.appointmentFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }

        return updatedAppointment;

    }

    // Finds and returns an appointment by its ID from the CSV.
    public Appointment findAppointmentById(int appointmentId) throws CSVFilesException, EntityNotFoundException {
        try (CSVReader appointmentReader = CSVUtil.getAppointmentReader()) {
            List<String[]> appointments = appointmentReader.readAll();
            appointments.removeFirst(); // Remove header row if present

            for (String[] appointmentData : appointments) {
                if (appointmentData.length > 0 && Integer.parseInt(appointmentData[0]) == appointmentId) {
                    return Appointment.builder()
                            .setId(Integer.parseInt(appointmentData[0]))
                            .setPatientId(Integer.parseInt(appointmentData[1]))
                            .setDoctorId(Integer.parseInt(appointmentData[2]))
                            .setDateTime(appointmentData[3])
                            .build();
                }
            }
            throw new EntityNotFoundException("Appointment with ID " + appointmentId + " not found.");
        } catch (CsvException | IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns all appointments associated with the given doctor ID.
    public List<Appointment> findAppointmentsByDoctorId(int doctorId) throws CSVFilesException {
        try (CSVReader appointmentReader = CSVUtil.getAppointmentReader()){
            List<String[]> appointments = appointmentReader.readAll();
            appointments.removeFirst(); // Remove header row if present

            List<Appointment> doctorAppointments = new java.util.ArrayList<>();
            for (String[] appointmentData : appointments) {
                if (appointmentData.length > 0 && Integer.parseInt(appointmentData[2]) == doctorId) {
                    doctorAppointments.add(Appointment.builder()
                            .setId(Integer.parseInt(appointmentData[0]))
                            .setPatientId(Integer.parseInt(appointmentData[1]))
                            .setDoctorId(Integer.parseInt(appointmentData[2]))
                            .setDateTime(appointmentData[3])
                            .build());
                }
            }
            return doctorAppointments;
        } catch (CsvException | IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Returns all appointments associated with the given patient ID.
    public List<Appointment> findAppointmentsByPatientId(int patientId) throws CSVFilesException {
        try(CSVReader appointmentReader = CSVUtil.getAppointmentReader()) {
            List<String[]> appointments = appointmentReader.readAll();
            appointments.removeFirst(); // Remove header row if present

            List<Appointment> patientAppointments = new java.util.ArrayList<>();
            for (String[] appointmentData : appointments) {
                if (appointmentData.length > 0 && Integer.parseInt(appointmentData[1]) == patientId) {
                    patientAppointments.add(Appointment.builder()
                            .setId(Integer.parseInt(appointmentData[0]))
                            .setPatientId(Integer.parseInt(appointmentData[1]))
                            .setDoctorId(Integer.parseInt(appointmentData[2]))
                            .setDateTime(appointmentData[3])
                            .build());
                }
            }
            return patientAppointments;
        } catch (CsvException | IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }

    // Cancels each appointment in the provided list by updating their status to CANCELED.
    public void cancelAppointments(List<Appointment> appointments) throws CSVFilesException, EntityNotFoundException {
        for (Appointment appointment : appointments) {
            updateAppointmentStatus(appointment.getId(), "CANCELED");
        }
    }

    // Reads and returns all appointments from the CSV file.
    public List<Appointment> findAllAppointments() throws CSVFilesException {
        try(CSVReader appointmentReader = CSVUtil.getAppointmentReader()) {
            List<String[]> appointments = appointmentReader.readAll();
            appointments.removeFirst(); // Remove header row if present

            List<Appointment> allAppointments = new java.util.ArrayList<>();
            for (String[] appointmentData : appointments) {
                if (appointmentData.length > 0) {
                    allAppointments.add(Appointment.builder()
                            .setId(Integer.parseInt(appointmentData[0]))
                            .setPatientId(Integer.parseInt(appointmentData[1]))
                            .setDoctorId(Integer.parseInt(appointmentData[2]))
                            .setDateTime(appointmentData[3])
                            .build());
                }
            }
            return allAppointments;
        } catch (CsvException | IOException e) {
            throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }
    }
}
