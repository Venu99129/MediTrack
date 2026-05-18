package org.airtribe.Services;

import org.airtribe.entity.Appointment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceTest {

    static class FakeAppointmentRepository extends org.airtribe.repository.AppointmentRepository {
        @Override
        public Appointment createAppointment(Appointment appointment) {
            return appointment;
        }

        @Override
        public Appointment findAppointmentById(int appointmentId) {
            return Appointment.builder().setId(appointmentId).setPatientId(1).setDoctorId(1).setDateTime("2026-01-01T10:00").build();
        }

        @Override
        public java.util.List<Appointment> findAppointmentsByPatientId(int patientId) {
            return java.util.Collections.emptyList();
        }
    }

    @Test
    public void bookAppointment_createsAndReturnsAppointment() throws Exception {
        AppointmentService service = new AppointmentService(new FakeAppointmentRepository(), new EmailNotification());
        Appointment ap = service.bookAppointment("1","1","2026-01-01T10:00");
        assertNotNull(ap);
        assertEquals(1, ap.getPatientId());
    }
}
