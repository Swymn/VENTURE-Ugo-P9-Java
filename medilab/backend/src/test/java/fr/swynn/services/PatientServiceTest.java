package fr.swynn.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.swynn.models.Patient;
import fr.swynn.repository.FakePatientRepository;

class PatientServiceTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        var repository = new FakePatientRepository();
        patientService = new DefaultPatientService(repository);
    }

    private Patient createFakePatient(UUID uuid) throws ParseException {
        var date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        return new Patient(uuid, "John", "Doe", date, "Male", Optional.empty(), Optional.empty());
    }

    @Test
    void updatePatient_shouldUpdatePatient_existingPatient() throws ParseException {
        // GIVEN a patient service
        // AND an existing patient
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakePatient(uuid);

        // WHEN updating the patient
        var updatedPatient = patientService.updatePatient(uuid, patient);

        // THEN the patient should be updated
        Assertions.assertTrue(updatedPatient.isPresent());
        Assertions.assertEquals(patient, updatedPatient.get());
    }
    
    @Test
    void updatePatient_shouldNotUpdatePatient_nonExistingPatient() throws ParseException {
        // GIVEN a patient service
        // AND a non-existing patient
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        var nonExistingPatient = createFakePatient(uuid);

        // WHEN updating the patient
        var updatedPatient = patientService.updatePatient(uuid, nonExistingPatient);

        // THEN the patient should not be updated
        Assertions.assertTrue(updatedPatient.isEmpty());
    }
}
