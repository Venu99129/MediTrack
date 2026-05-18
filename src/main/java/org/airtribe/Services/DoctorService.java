package org.airtribe.Services;

import org.airtribe.entity.Doctor;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.repository.DoctorRepository;
import org.airtribe.util.Counters;

import java.util.List;

public class DoctorService {

    // Repository handles all CSV read/write for doctors
    DoctorRepository doctorRepository;

    // Default constructor uses the concrete repository
    public DoctorService() {
        this.doctorRepository = new DoctorRepository();
    }

    // Injectable constructor used in unit tests to provide a mock repository
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Auto-generate next doctor ID; specialization is stored as the 4th CSV column
    public Doctor addDoctor(String name, String specialization, String email) throws CSVFilesException {
        Doctor doctor = new Doctor(Counters.getNextDoctorId(), name, email, specialization);
        // Persist to CSV and return the saved entity
        return doctorRepository.saveDoctorData(doctor);
    }

    // Delegates lookup; both CSV and not-found exceptions bubble to the caller
    public Doctor getDoctorById(int id) throws CSVFilesException, EntityNotFoundException {
        return doctorRepository.findDoctorById(id);
    }

    // Delete swallows exceptions; Main.java handles display
    public void deleteDoctorById(int id) {
        try {
            doctorRepository.deleteDoctorDataById(id);
            System.out.println("Doctor with ID " + id + " has been deleted.");
        } catch (EntityNotFoundException e) {
            // Record not present: message only
            System.out.println("Doctor with ID " + id + " not found: " + e.getMessage());
        } catch (CSVFilesException e) {
            // CSV I/O failure: print with stack trace
            System.out.println("Error deleting doctor data: " + e.getMessage());
            System.out.println("Stack trace: " + e.getStackTrace());
        }
    }

    // Returns the full list from CSV; caller handles empty-list display
    public List<Doctor> getAllDoctors() throws CSVFilesException {
        return doctorRepository.findAllDoctors();
    }

    // Generic search using SearchService + Doctor.match(key)
    public List<Doctor> searchDoctors(String key) throws CSVFilesException {
        return SearchService.search(doctorRepository.findAllDoctors(), key);
    }
}
