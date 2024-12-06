package fr.swynn.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.swynn.dto.PatientDto;
import fr.swynn.models.Patient;
import fr.swynn.services.PatientService;
import jakarta.validation.Valid;

@RestController
public class PatientController {

    private final PatientService patientService;
    private final PublicObjectMapper mapper;

    @Autowired
    public PatientController(final PatientService patientService) {
        this.patientService = patientService;
        this.mapper = new PublicObjectMapper();
    }

    @PatchMapping("/patients/{identifier}")
    public ResponseEntity<Patient> updatePatientFirstName(final @PathVariable UUID identifier, final @Valid @RequestBody PatientDto patient) {
        var mappedPatient = mapper.map(patient);
        var updatedPatient = patientService.updatePatient(identifier, mappedPatient);
        return updatedPatient
            .map(existingPatient -> ResponseEntity.ok(existingPatient))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
