package org.airtribe.Services;

import org.airtribe.entity.Appointment;
import org.airtribe.entity.Stratergy.Bill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorConsultingServiceTest {

    static class FakeAppointmentService extends AppointmentService {
        public FakeAppointmentService() {
            super(new org.airtribe.repository.AppointmentRepository(), new org.airtribe.Services.EmailNotification());
        }

        @Override
        public Appointment updateAppointmentStatus(int appointmentId, String newStatus) {
            // simulate success and return a dummy appointment
            return Appointment.builder().setId(appointmentId).setPatientId(1).setDoctorId(1).setDateTime("2026-01-01T10:00").build();
        }
    }

    static class FakeBillService extends BillService {
        public FakeBillService() {
            super(new org.airtribe.repository.BillRepository());
        }

        @Override
        public void saveBill(org.airtribe.entity.BillSummary billSummary) {
            // simulate successful save
        }
    }

    @Test
    public void consultDoctor_withSuccessfulPayment_savesBill() throws Exception {
        PaymentService paymentService = new PaymentService(amount -> true);
        DoctorConsultingService service = new DoctorConsultingService(new FakeAppointmentService(), paymentService, new FakeBillService());

        Bill bill = service.consultDoctor(1, 1, 1);
        assertNotNull(bill);
        assertTrue(bill.calculateTotalAmount() > 0);
    }
}
