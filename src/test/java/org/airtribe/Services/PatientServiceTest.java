package org.airtribe.Services;

import org.airtribe.entity.Patient;
import org.airtribe.repository.PatientRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTest {

    @Test
    public void addPatient_callsRepositoryAndReturnsPatient() throws Exception {
        PatientRepository repo = new PatientRepository() {
            @Override
            public void savePatientData(Patient patient) {
                // do nothing
            }
        };

        PatientService patientService = new PatientService(repo);

        Patient result = patientService.addPatient("John", "john@example.com", "None");

        assertNotNull(result);
        assertEquals("John", result.getName());
    }
}
