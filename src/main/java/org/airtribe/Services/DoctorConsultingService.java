package org.airtribe.Services;

import org.airtribe.entity.Appointment;
import org.airtribe.entity.BillSummary;
import org.airtribe.entity.Enum.AppointmentStatus;
import org.airtribe.entity.Stratergy.*;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.repository.BillRepository;
import org.airtribe.util.Counters;

public class DoctorConsultingService {

    AppointmentService appointmentService;
    PaymentService paymentService;
    BillService billService;

    public DoctorConsultingService(AppointmentService appointmentService, PaymentService paymentService, BillService billService) {
        this.appointmentService = appointmentService;
        this.paymentService = paymentService;
        this.billService = billService;
    }

    public Bill consultDoctor(int appointmentId, int patientId, int doctorId) throws CSVFilesException, EntityNotFoundException {
        // attend appointment
        appointmentService.updateAppointmentStatus(appointmentId, AppointmentStatus.COMPLETED.toString());

        // Simulate consultation and generate bill
        Bill bill = new BaseBill(Counters.getNextBillId(),patientId,appointmentId, 100.0); // Example bill amount
        bill = new AppointmentBill(bill, 500.0); // Adding consultation fee to the bill
        bill = new LabBill(bill, 200.0); // Adding lab test fee to the bill
        bill = new MedicalBill(bill, 150.0); // Adding medication fee to the bill

        // Process payment
        boolean paymentSuccess = paymentService.processPayment(bill.calculateTotalAmount());

        if (paymentSuccess) {
            try {
                BillSummary billSummary = new BillSummary(bill.getId(), bill.getPatientId(), bill.getAppointmentId(), bill.calculateTotalAmount());
                billService.saveBill(billSummary);
            } catch (Exception e) {
                System.out.println("Error saving bill: " + e.getMessage());
            }
        }
        return bill;
    }
}
