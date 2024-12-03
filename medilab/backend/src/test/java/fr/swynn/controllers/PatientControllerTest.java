package fr.swynn.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

import fr.swynn.models.Patient;
import fr.swynn.services.FakePatientService;

class PatientControllerTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String DEFAULT_FIRST_NAME = "John";
    private static final String DEFAULT_LAST_NAME = "Doe";
    private static final String DEFAULT_GENDER = "Male";
    private static final String DEFAULT_DATE = "2023-10-01T00:00:00Z";
    
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        var fakePatientService = new FakePatientService();
        patientController = new PatientController(fakePatientService);
    }

    private Patient createFakePatient(final UUID identifier, final String firstName, final String lastName) throws ParseException {
        var date = DATE_FORMAT.parse(DEFAULT_DATE);
        return new Patient(identifier, firstName, lastName, date, DEFAULT_GENDER, Optional.empty(), Optional.empty());
    }

    private Patient createFakePatient(final UUID identifier, final String phoneNumber) throws ParseException {
        var date = DATE_FORMAT.parse(DEFAULT_DATE);
        return new Patient(identifier, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, date, DEFAULT_GENDER, Optional.empty(), Optional.of(phoneNumber));
    }

    @Test
    void updatePatient_updatePatientFirstName_existingPatient() throws ParseException {
        // GIVEN a patient controller with an existing patient
        var patientIdentifier = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var newFirstName = "Roger";
        var newLastName = "Oxendale";
        var patient = createFakePatient(patientIdentifier, newFirstName, newLastName);

        // WHEN updating the patient first name
        var response = patientController.updatePatientFirstName(patientIdentifier, patient);

        // THEN the patient first name should be updated
        var updatedPatient = response.getBody();
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals(newFirstName, updatedPatient.firstName());
        Assertions.assertEquals(newLastName, updatedPatient.lastName());
    }

    @Test
    void updatePatient_notFound_missingPatient() throws ParseException {
        // GIVEN a patient controller with an existing patient
        var patientIdentifier = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        var newFirstName = "Roger";
        var newLastName = "Oxendale";
        var patient = createFakePatient(patientIdentifier, newFirstName, newLastName);

        // WHEN updating the patient first name
        var response = patientController.updatePatientFirstName(patientIdentifier, patient);

        // THEN the patient should not be found
        Assertions.assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    void updatePatient_addNewField_existingPatient() throws ParseException {
        // GIVEN a patient controller with an existing patient
        var patientIdentifier = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var newPhoneNumber = "+1-555-123-4567";
        var patient = createFakePatient(patientIdentifier, newPhoneNumber);

        // WHEN updating the patient first name
        var response = patientController.updatePatientFirstName(patientIdentifier, patient);

        // THEN the patient first name should be updated
        var updatedPatient = response.getBody();
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals(newPhoneNumber, updatedPatient.phoneNumber().get());
    }
}
