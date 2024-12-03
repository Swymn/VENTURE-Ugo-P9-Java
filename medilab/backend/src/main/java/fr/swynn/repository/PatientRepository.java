package fr.swynn.repository;

import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public interface PatientRepository {
    
    /**
     * Update a patient
     * @param patientIdentifier the identifier of the patient
     * @param patient the updated patient
     * @return the updated patient
     */
    Optional<Patient> updatePatient(UUID patientIdentifier, Patient patient);
}
