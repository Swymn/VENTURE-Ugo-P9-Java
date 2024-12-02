package fr.swynn.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.swynn.models.Patient;
import fr.swynn.services.PatientService;

@RestController
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(final PatientService patientService) {
        this.patientService = patientService;
    }
    
    @PatchMapping("/patients/{identifier}")
    public ResponseEntity<Patient> updatePatientFirstName(@PathVariable UUID identifier, @RequestBody Patient patient) {
        var updatedPatient = patientService.updatePatient(identifier, patient);
        return updatedPatient
            .map(existingPatient -> ResponseEntity.ok(existingPatient))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
