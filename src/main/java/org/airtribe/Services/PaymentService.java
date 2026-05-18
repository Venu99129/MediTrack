package org.airtribe.Services;

import org.airtribe.Interface.Payable;

public class PaymentService {

    Payable paymentMethod;

    // Constructor that injects the Payable implementation used for payments.
    public PaymentService(Payable paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Processes a payment by delegating to the configured Payable implementation.
    public boolean processPayment(double amount) {
        return paymentMethod.makePayment(amount);
    }
}
