package fr.swynn.controllers;

import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.swynn.models.Patient;
import fr.swynn.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class PatientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);
    
    private final PatientService patientService;

    public PatientController(final PatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping("/patients/{identifier}")
    public ResponseEntity<Patient> updatePatient(final @PathVariable UUID identifier, final @Valid @RequestBody Patient patient) {
        LOGGER.debug("Updating patient with identifier: {}", identifier);
        var updatedPatient = patientService.updatePatient(identifier, patient);
        return updatedPatient
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/patients")
    public ResponseEntity<Collection<Patient>> getPatients() {
        LOGGER.debug("Retrieving all patients");
        var patients = patientService.getPatients();
        return ResponseEntity.ok(patients);
    }
}
