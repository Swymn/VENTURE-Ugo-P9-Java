package fr.swynn.services;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public interface PatientService {

    /**
     * Update a patient
     * @param patientIdentifier the identifier of the patient
     * @param patient the updated patient
     * @return the updated patient
     */
    Optional<Patient> updatePatient(UUID patientIdentifier, Patient patient);

    /**
     * Get all patients
     * @return all patients
     */
    Collection<Patient> getPatients();
}
