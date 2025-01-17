package fr.swynn.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;

import fr.swynn.models.Patient;
import fr.swynn.services.PatientService;

class PatientControllerTest {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String DEFAULT_FIRST_NAME = "John";
    private static final String DEFAULT_LAST_NAME = "Doe";
    private static final String DEFAULT_GENDER = "Male";
    private static final String DEFAULT_DATE = "2023-10-01T00:00:00Z";
    
    private PatientController patientController;
    private PatientService mockService;

    @BeforeEach
    void setUp() {
        mockService = Mockito.mock(PatientService.class);
        patientController = new PatientController(mockService);
    }

    private Patient createFakePatient(final UUID identifier, final String firstName, final String lastName) throws ParseException {
        var date = DATE_FORMAT.parse(DEFAULT_DATE);
        var creationDate = new Date();
        return new Patient(identifier, creationDate, creationDate, firstName, lastName, date, DEFAULT_GENDER, null, null);
    }

    private Patient createFakePatient(final UUID identifier, final String phoneNumber) throws ParseException {
        var date = DATE_FORMAT.parse(DEFAULT_DATE);
        var creationDate = new Date();
        return new Patient(identifier, creationDate, creationDate, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, date, DEFAULT_GENDER, null, phoneNumber);
    }

    private Patient createFakePatient(final UUID identifier) throws ParseException {
        var date = DATE_FORMAT.parse(DEFAULT_DATE);
        var creationDate = new Date();
        return new Patient(identifier, creationDate, creationDate, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, date, DEFAULT_GENDER, null, null);
    }

    @Test
    void updatePatient_updatePatientFirstName_existingPatient() throws ParseException {
        // GIVEN a patient controller with an existing patient
        var patientIdentifier = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var newFirstName = "Roger";
        var newLastName = "Oxendale";
        var patient = createFakePatient(patientIdentifier, newFirstName, newLastName);

        // WHEN updating the patient first name
        Mockito.when(mockService.updatePatient(patientIdentifier, patient)).thenReturn(Optional.of(patient));
        var response = patientController.updatePatient(patientIdentifier, patient);

        // THEN the patient first name should be updated
        var updatedPatient = response.getBody();
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals(newFirstName, updatedPatient.getFirstName());
        Assertions.assertEquals(newLastName, updatedPatient.getLastName());
    }

    @Test
    void updatePatient_notFound_missingPatient() throws ParseException {
        // GIVEN a patient controller with an existing patient
        var patientIdentifier = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        var newFirstName = "Roger";
        var newLastName = "Oxendale";
        var patient = createFakePatient(patientIdentifier, newFirstName, newLastName);

        // WHEN updating the patient first name
        Mockito.when(mockService.updatePatient(patientIdentifier, patient)).thenReturn(Optional.empty());
        var response = patientController.updatePatient(patientIdentifier, patient);

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
        Mockito.when(mockService.updatePatient(patientIdentifier, patient)).thenReturn(Optional.of(patient));
        var response = patientController.updatePatient(patientIdentifier, patient);

        // THEN the patient first name should be updated
        var updatedPatient = response.getBody();
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals(newPhoneNumber, updatedPatient.getPhoneNumber());
    }

    @Test
    void getPatients_noPatients_emptyList() {
        // GIVEN a patient controller with no patients
        // WHEN getting the patients
        Mockito.when(mockService.getPatients()).thenReturn(Collections.emptyList());
        var response = patientController.getPatients();

        // THEN the patient list should be empty
        var patients = response.getBody();
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertTrue(patients.isEmpty());
    }

    @Test
    void getPatients_existingPatients_twoPatients() throws ParseException {
        // GIVEN a patient controller with two patients
        var patient1 = createFakePatient(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        var patient2 = createFakePatient(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));

        // WHEN getting the patients
        Mockito.when(mockService.getPatients()).thenReturn(List.of(patient1, patient2));
        var response = patientController.getPatients();

        // THEN the patient list should contain two patients
        var patients = response.getBody();
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals(2, patients.size());
    }
}
