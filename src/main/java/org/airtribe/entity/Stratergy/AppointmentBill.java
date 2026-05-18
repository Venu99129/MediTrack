package org.airtribe.entity.Stratergy;

public class AppointmentBill extends BillDecorator{

    double amount;

    public AppointmentBill(Bill bill, double amount){
        super(bill);
        this.amount = amount;
    }

    @Override
    public double calculateBaseAmount() {
        return bill.calculateBaseAmount() + amount;
    }

}
