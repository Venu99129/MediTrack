package org.airtribe.Services;

import org.airtribe.Interface.Payable;

public class UPIPayment implements Payable {

    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean makePayment(double amount) {
        // Simulate UPI payment processing
        System.out.println("Processing UPI payment of " + amount + " to " + upiId);
         // In a real implementation, you would integrate with a UPI payment gateway here
        return true;
    }
}
