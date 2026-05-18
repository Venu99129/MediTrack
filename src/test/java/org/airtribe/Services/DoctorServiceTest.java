package org.airtribe.Services;

import org.airtribe.entity.Doctor;
import org.airtribe.repository.DoctorRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorServiceTest {

    @Test
    public void addDoctor_callsRepositoryAndReturnsDoctor() throws Exception {
        // Fake repository that returns the passed doctor
        DoctorRepository repo = new DoctorRepository() {
            @Override
            public Doctor saveDoctorData(Doctor doctor) {
                return doctor;
            }
        };

        DoctorService doctorService = new DoctorService(repo);

        Doctor result = doctorService.addDoctor("Dr A", "Cardiology", "a@example.com");

        assertNotNull(result);
        assertEquals("Dr A", result.getName());
    }
}
