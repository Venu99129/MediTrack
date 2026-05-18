package org.airtribe.entity;

public final class BillSummary {
    private final int id;
    private final int patientId;
    private final int appointmentId;
    private final double totalAmount;

    public BillSummary(int id,int patientId,int appointmentId, double totalAmount) {
        this.id = id;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    @Override
    public String toString() {
        return "BillSummary{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", appointmentId=" + appointmentId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
