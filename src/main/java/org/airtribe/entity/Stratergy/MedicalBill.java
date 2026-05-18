package org.airtribe.entity.Stratergy;

public class MedicalBill extends BillDecorator{

    double amount;

    public MedicalBill(Bill bill, double amount) {
        super(bill);
    }
    @Override
    public double calculateBaseAmount() {
        return bill.calculateBaseAmount() + amount;
    }

}
