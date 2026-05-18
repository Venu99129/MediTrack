package org.airtribe.Services;

import org.airtribe.entity.Patient;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.EntityNotFoundException;
import org.airtribe.repository.PatientRepository;
import org.airtribe.util.Counters;

import java.util.Arrays;
import java.util.List;

public class PatientService {

    // Repository handles all CSV read/write for patients
    PatientRepository patientRepository;

    // Default constructor uses concrete repository
    public PatientService() {
        this.patientRepository = new PatientRepository();
    }

    // Injectable constructor for unit tests
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Auto-generate the next patient ID via the shared counter
    public Patient addPatient(String name, String email, String medicalHistory) throws CSVFilesException {
        Patient patient = new Patient(Counters.getNextPatientId(), name, email, medicalHistory);
        // Persist the patient to CSV storage
        patientRepository.savePatientData(patient);
        return patient;
    }

    // Delegates lookup to repository; both CSV and not-found exceptions bubble up
    public Patient getPatientById(int id) throws CSVFilesException, EntityNotFoundException {
        return patientRepository.findPatientById(id);
    }

    // Delete swallows exceptions here; Main.java decides how to display errors
    public void deletePatientById(int id) {
        try {
            patientRepository.deletePatientDataById(id);
            System.out.println("Patient with ID " + id + " has been deleted.");
        } catch (CSVFilesException e) {
            // CSV I/O failure: print with stack trace
            System.out.println("Error deleting patient data: " + e.getMessage());
            System.out.println("Stack trace: " + Arrays.toString(e.getStackTrace()));
        } catch (EntityNotFoundException e) {
            // Record simply not present: message only
            System.out.println("Patient with ID " + id + " not found: " + e.getMessage());
        }
    }

    // Print all patients directly (used internally; caller should prefer searchPatients(""))
    public List<Patient> getAllPatients() throws CSVFilesException {

        List<Patient> patients = patientRepository.findAllPatients();
        return patients;

    }

    // Generic search: delegates to SearchService which filters via Searchable.match()
    public List<Patient> searchPatients(String key) throws CSVFilesException {
        return SearchService.search(patientRepository.findAllPatients(), key);
    }
}
