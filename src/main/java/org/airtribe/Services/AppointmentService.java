package org.airtribe.Services;

import org.airtribe.Interface.Notification;
import org.airtribe.entity.Appointment;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.repository.AppointmentRepository;
import org.airtribe.util.Counters;

import java.util.List;

public class AppointmentService {

    // Repository for CSV persistence; Notification for patient alerts
    AppointmentRepository appointmentRepository;
    Notification notificationService;

    // Constructor injection allows easy mocking in tests
    public AppointmentService(AppointmentRepository appointmentRepository, Notification notificationService) {
        this.appointmentRepository = appointmentRepository;
        this.notificationService = notificationService;
    }

    // Build appointment via Builder pattern, persist it, then notify the patient
    public Appointment bookAppointment(String userId, String doctorId, String appointmentTime) throws CSVFilesException {
        Appointment appointment = Appointment.builder()
                .setId(Counters.getNextAppointmentId())
                .setPatientId(Integer.parseInt(userId))
                .setDoctorId(Integer.parseInt(doctorId))
                .setDateTime(appointmentTime)
                .build();

        System.out.println("Booking appointment for user " + userId + " with doctor " + doctorId + " at " + appointmentTime);
        appointmentRepository.createAppointment(appointment);
        // Notify patient immediately after booking
        notificationService.sendNotification(appointment.getPatientId(),
                "Your appointment with doctor " + doctorId + " is booked for " + appointmentTime);
        return appointment;
    }

    // Fetch the appointment first (to get patientId for notification), then update status
    public Appointment updateAppointmentStatus(int appointmentId, String newStatus) throws CSVFilesException, EntityNotFoundException {
        System.out.println("Updating appointment " + appointmentId + " to status " + newStatus);
        // Notify patient about the status change
        notificationService.sendNotification(
                appointmentRepository.findAppointmentById(appointmentId).getPatientId(),
                "Your appointment status has been updated to " + newStatus);
        return appointmentRepository.updateAppointmentStatus(appointmentId, newStatus);
    }

    // Simple passthrough to repository; printing kept for traceability
    public Appointment getAppointmentDetails(int appointmentId) throws CSVFilesException, EntityNotFoundException {
        System.out.println("Retrieving details for appointment " + appointmentId);
        return appointmentRepository.findAppointmentById(appointmentId);
    }

    // Retrieve all appointments for a specific patient
    public List<Appointment> getAppointmentsByPatientId(int patientId) throws CSVFilesException {
        System.out.println("Retrieving appointments for user " + patientId);
        return appointmentRepository.findAppointmentsByPatientId(patientId);
    }

    // Retrieve all appointments for a specific doctor
    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws CSVFilesException {
        System.out.println("Retrieving appointments for doctor " + doctorId);
        return appointmentRepository.findAppointmentsByDoctorId(doctorId);
    }

    // Retrieve every appointment regardless of patient or doctor
    public List<Appointment> getAllAppointments() throws CSVFilesException {
        System.out.println("Retrieving all appointments");
        return appointmentRepository.findAllAppointments();
    }

    // Cancel a list of appointments then notify each affected patient
    public void cancelAppointments(List<Appointment> appointments) throws CSVFilesException, EntityNotFoundException {
        System.out.println("Canceling appointments: " + appointments);
        appointmentRepository.cancelAppointments(appointments);
        // Send a cancellation notification for every appointment in the list
        appointments.forEach(appointment ->
                notificationService.sendNotification(appointment.getPatientId(),
                        "Your appointment with doctor " + appointment.getDoctorId() + " has been canceled."));
    }
}
