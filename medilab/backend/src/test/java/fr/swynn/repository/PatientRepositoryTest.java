package fr.swynn.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.swynn.models.Patient;

class PatientRepositoryTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = new InMemoryPatientRepository();
    }

    private Patient createFakePatient(final UUID uuid) throws ParseException {
        var date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        var creationDate = new Date();
        return new Patient(uuid, creationDate, creationDate, "John", "Doe", date, "Male", Optional.empty(), Optional.empty());
    }

    private Patient createFakePatient(final UUID uuid, final String firstName) throws ParseException {
        var date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        var creationDate = new Date();
        return new Patient(uuid, creationDate, creationDate, firstName, "Doe", date, "Male", Optional.empty(), Optional.empty());
    }

    private void populateRepository() throws ParseException {
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakePatient(uuid);
        patientRepository.createPatient(patient);
    }

    @Test
    void createPatient_newPatient_newPatient() throws ParseException {
        // GIVEN a patient repository
        // AND a patient
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakePatient(uuid);

        // WHEN creating the patient
        var createdPatient = patientRepository.createPatient(patient);

        // THEN the patient should be created
        Assertions.assertTrue(createdPatient.isPresent());
        Assertions.assertEquals(patient, createdPatient.get());
    }

    @Test
    void createPatient_emptyPatient_existingPatient() throws ParseException {
        // GIVEN a patient repository with an existing patient
        populateRepository();
        // AND a patient
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakePatient(uuid);

        // WHEN creating the patient
        var createdPatient = patientRepository.createPatient(patient);

        // THEN the patient should be created
        Assertions.assertTrue(createdPatient.isEmpty());
    }

    @Test
    void updatePatient_updatePatient_existingPatient() throws ParseException {
        // GIVEN a patient repository with an existing patient
        populateRepository();
        // AND a patient
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakePatient(uuid, "Jane");

        // WHEN updating the patient
        var updatedPatient = patientRepository.updatePatient(uuid, patient);

        // THEN the patient should be updated
        Assertions.assertTrue(updatedPatient.isPresent());
        Assertions.assertEquals(patient, updatedPatient.get());
        Assertions.assertEquals("Jane", updatedPatient.get().getFirstName());
    }
}
