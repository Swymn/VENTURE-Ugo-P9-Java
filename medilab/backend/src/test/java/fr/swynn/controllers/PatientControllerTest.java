package fr.swynn.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

import fr.swynn.services.FakePatientService;

class PatientControllerTest {

    private PatientController patientController;

    @BeforeEach()
    void setUp() {
        var FakePatientService = new FakePatientService();
        patientController = new PatientController(FakePatientService);
    }

    @Test
    void updatePatientFirstName_updatePatientName_existingPatient() {
        // GIVEN a patient controller with an existing patient
        // AND a patient identifier
        var patientIdentifier = "550e8400-e29b-41d4-a716-446655440000";
        // AND a new first name
        var newFirstName = "Edgar";

        // WHEN updating the patient first name
        var response = patientController.updatePatientFirstName(patientIdentifier, newFirstName);

        // THEN the patient first name should be updated
        var updatedPatient = response.getBody();
        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(newFirstName, updatedPatient.firstName());
    }

    @Test
    void updatePatientFirstName_internalError_invalidPatientIdentifier() {
        // GIVEN a patient controller with an existing patient
        // AND an invalid patient identifier
        var patientIdentifier = "invalid";
        // AND a new first name
        var newFirstName = "Edgar";

        // WHEN updating the patient first name
        var response = patientController.updatePatientFirstName(patientIdentifier, newFirstName);

        // THEN an internal error should be returned
        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(500));
    }

    @Test
    void updatePatientFirstName_notFound_nonExistingPatient() {
        // GIVEN a patient controller with an existing patient
        // AND a non-existing patient identifier
        var patientIdentifier = "550e8400-e29b-41d4-a716-446655440001";
        // AND a new first name
        var newFirstName = "Edgar";

        // WHEN updating the patient first name
        var response = patientController.updatePatientFirstName(patientIdentifier, newFirstName);

        // THEN a not found error should be returned
        Assertions.assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(404));
    }
}
