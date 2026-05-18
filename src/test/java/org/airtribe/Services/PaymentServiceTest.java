package org.airtribe.Services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    @Test
    public void processPayment_cardAndUPI_returnTrue() {
        CardPayment card = new CardPayment("1111222233334444", "John", "12/25", "123");
        PaymentService psCard = new PaymentService(card);
        assertTrue(psCard.processPayment(100.0));

        UPIPayment upi = new UPIPayment("john@upi");
        PaymentService psUpi = new PaymentService(upi);
        assertTrue(psUpi.processPayment(50.0));
    }
}
