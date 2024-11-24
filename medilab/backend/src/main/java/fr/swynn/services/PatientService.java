package fr.swynn.services;

import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public interface PatientService {
    
    /**
     * Update the first name of a patient
     * 
     * @param patientIdentifier the identifier of the patient
     * @param newFirstName the new first name
     * @return the updated patient
     */
    Optional<Patient> updatePatientFirstName(UUID patientIdentifier, String newFirstName);
}
