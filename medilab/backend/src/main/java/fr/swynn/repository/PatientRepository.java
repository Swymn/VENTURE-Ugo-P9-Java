package fr.swynn.repository;

import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public interface PatientRepository {

    /**
     * Create a patient
     * @param patient the patient to create
     * @return the created patient
     */
    Optional<Patient> createPatient(Patient patient);
    
    /**
     * Update a patient
     * @param patientIdentifier the identifier of the patient
     * @param patient the updated patient
     * @return the updated patient
     */
    Optional<Patient> updatePatient(UUID patientIdentifier, Patient patient);
}
