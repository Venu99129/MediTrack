package org.airtribe.entity.Stratergy;

import org.airtribe.Constants.TaxRates;

public abstract class Bill {
    private int id;
    private int patientId;
    private int appointmentId;
    private double tax;


    public Bill(int id, int patientId,int appointmentId) {
        this.id = id;
        this.tax = TaxRates.medicalTaxRate;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public double getTax() {
        return tax;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public abstract double calculateBaseAmount();

    public  double calculateTotalAmount(){
        return calculateBaseAmount() + ((calculateBaseAmount()/100) * tax);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", patientId=" + patientId +
                "appointmentId=" + appointmentId +
                ", tax=" + tax +
                ", totalAmount=" + calculateTotalAmount() +
                '}';
    }
}
