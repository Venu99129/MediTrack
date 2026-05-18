package org.airtribe.Services;

import org.airtribe.Interface.Notification;

public class EmailNotification implements Notification {

    @Override
    public void sendNotification(int patientId,String message) {
        // Simulate sending an email notification
        System.out.println("Email sent to patient : " + message);
    }

}
