package org.airtribe.entity.Stratergy;

public class LabBill extends BillDecorator{

    double amount;

    public LabBill(Bill bill, double amount) {
        super(bill);
        this.amount = amount;
    }
    @Override
    public double calculateBaseAmount() {
        return bill.calculateBaseAmount()+amount;
    }
}
