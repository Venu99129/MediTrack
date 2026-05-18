package org.airtribe.Interface;

public interface Notification {

    //sends the notification method implements in child classes
    void sendNotification(int patientId, String message);
}
