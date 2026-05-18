package org.airtribe.entity;

import org.airtribe.Interface.Searchable;

public class Doctor extends Person implements Searchable {
    private int id;
    private String specialization;

    public Doctor(int id,String name, String email, String specialization) {

        super(name, email);
        this.specialization = specialization;
        this.id = id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + this.getName() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }

    @Override
    public boolean match(String key) {
        key = key.toLowerCase();

        return String.valueOf(id).contains(key) ||
                this.getName().toLowerCase().contains(key.toLowerCase()) ||
                this.getEmail().toLowerCase().contains(key.toLowerCase()) ||
                this.specialization.toLowerCase().contains(key.toLowerCase());
    }
}
