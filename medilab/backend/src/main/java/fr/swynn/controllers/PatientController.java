package fr.swynn.controllers;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @PatchMapping("/patients/{identifier}/firstname")
    public ResponseEntity<Patient> updatePatientFirstName(@PathVariable String identifier, @RequestParam String firstName) {
        return updatePatientDetail(identifier, firstName, (id, value) -> patientService.updatePatientFirstName(id, value));
    }

    @PatchMapping("/patients/{identifier}/address")
    public ResponseEntity<Patient> addPostalAddress(@PathVariable String identifier, @RequestParam String address) {
        return updatePatientDetail(identifier, address, (id, value) -> patientService.addPostalAddress(id, value));
    }

    private ResponseEntity<Patient> updatePatientDetail(String identifier, String value, BiFunction<UUID, String, Optional<Patient>> updateFunction) {
        try {
            var patientIdentifier = UUID.fromString(identifier);
            var updatedPatient = updateFunction.apply(patientIdentifier, value);
            
            if (updatedPatient.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(updatedPatient.get());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
