package org.airtribe.entity.Stratergy;

public class BaseBill extends Bill {

    double amount;

    public
    BaseBill(int id, int patientId,int appointmentId,double amount) {
        super(id, patientId, appointmentId);
        this.amount = amount;
    }

    @Override
    public double calculateBaseAmount() {
        return amount;
    }

}
