package org.airtribe.entity;

import org.airtribe.entity.Enum.AppointmentStatus;

public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private String dateTime;
    private AppointmentStatus status;

    private Appointment(Bilder bilder) {
        this.id = bilder.id;
        this.doctorId = bilder.doctorId;
        this.patientId = bilder.patientId;
        this.dateTime = bilder.dateTime;
        this.status = AppointmentStatus.SCHEDULED; // Default status when creating an appointment
    }

    public int getId() {
        return id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public static Bilder builder() {
        return new Bilder();
    }

    public void completeAppointment(){
        this.status = AppointmentStatus.COMPLETED;
    }

    public void cancelAppointment(){
        this.status = AppointmentStatus.CANCELED;
    }

    public String getStatus() {
        return status.name();
    }

    public static class Bilder{
        private int id;
        private int doctorId;
        private int patientId;
        private String dateTime;

        public Bilder setId(int id) {
            this.id = id;
            return this;
        }
        public Bilder setDoctorId(int doctorId) {
            this.doctorId = doctorId;
            return this;
        }
        public Bilder setPatientId(int patientId) {
            this.patientId = patientId;
            return this;
        }
        public Bilder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }
        public Appointment build() {
            return new Appointment(this);
        }
    }

    @Override
    public String
    toString() {
        return "Appointment{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", dateTime='" + dateTime + '\'' +
                ", status=" + status +
                '}';
    }
}
