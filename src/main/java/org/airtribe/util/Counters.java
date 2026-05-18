package org.airtribe.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counters {

    private static final int MIN_ID = 1000;
    private static final int MAX_ID = 9999;

    private static final AtomicInteger patientIdCounter = new AtomicInteger(MIN_ID);
    private static final AtomicInteger billIdCounter = new AtomicInteger(MIN_ID);
    private static final AtomicInteger doctorIdCounter = new AtomicInteger(MIN_ID);
    private static final AtomicInteger appointmentIdCounter = new AtomicInteger(MIN_ID);

    // Generates the next unique ID from the provided atomic counter, enforcing the MAX_ID limit.
    private static int nextId(AtomicInteger counter) {
        int id = counter.getAndIncrement();
        if (id > MAX_ID) {
            throw new IllegalStateException("No more unique 4-digit IDs available");
        }
        return id;
    }

    // Returns the next unique patient ID.
    public static int getNextPatientId() {
        return nextId(patientIdCounter);
    }

    // Returns the next unique bill ID.
    public static int getNextBillId() {
        return nextId(billIdCounter);
    }

    // Returns the next unique doctor ID.
    public static int getNextDoctorId() {
        return nextId(doctorIdCounter);
    }

    // Returns the next unique appointment ID.
    public static int getNextAppointmentId() {
        return nextId(appointmentIdCounter);
    }
}