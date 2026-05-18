package org.airtribe.entity.Stratergy;

public abstract class BillDecorator extends Bill {

    Bill bill;

    public BillDecorator(Bill bill) {
        super(bill.getId(),bill.getPatientId(),bill.getAppointmentId());
        this.bill = bill;
    }

}
