package fr.swynn.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.swynn.models.Patient;
import fr.swynn.services.PatientService;
import jakarta.validation.Valid;

@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(final PatientService patientService) {
        this.patientService = patientService;
    }

    @PutMapping("/patients/{identifier}")
    public ResponseEntity<Patient> updatePatient(final @PathVariable UUID identifier, final @Valid @RequestBody Patient patient) {
        var updatedPatient = patientService.updatePatient(identifier, patient);
        return updatedPatient
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
