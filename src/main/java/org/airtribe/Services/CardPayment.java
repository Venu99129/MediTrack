package org.airtribe.Services;

import org.airtribe.Interface.Payable;

public class CardPayment implements Payable {

    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean makePayment(double amount) {
        // Simulate card payment processing
        System.out.println("Processing card payment of " + amount + " for " + cardHolderName);
         // In a real implementation, you would integrate with a card payment gateway here
        return true;
    }
}
