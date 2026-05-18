package org.airtribe.entity;

import org.airtribe.Interface.Searchable;

public class Patient extends Person implements Searchable {
    private int id;
    private String medicalHistory;

    public Patient(int id, String name, String email, String medicalHistory) {
        super(name, email);
        this.id = id;
        this.medicalHistory = medicalHistory;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }

    @Override
    public boolean match(String key) {

        key = key.toLowerCase();

        return String.valueOf(id).contains(key) ||
                this.getName().toLowerCase().contains(key.toLowerCase()) ||
                this.getEmail().toLowerCase().contains(key.toLowerCase()) ||
                this.medicalHistory.toLowerCase().contains(key.toLowerCase());
    }
}
