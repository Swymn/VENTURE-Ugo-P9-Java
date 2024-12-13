package fr.swynn.repository;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fr.swynn.models.Patient;

@Repository
public class InMemoryPatientRepository implements PatientRepository {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryPatientRepository.class);

    private final HashMap<UUID, Patient> patients;

    public InMemoryPatientRepository() {
        this.patients = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> createPatient(final Patient patient) {
        if (patients.containsKey(patient.getIdentifier())) {
            LOGGER.debug("Patient with identifier {} already exists", patient.getIdentifier());
            return Optional.empty();
        }
        patients.put(patient.getIdentifier(), patient);
        LOGGER.debug("Patient with identifier {} created", patient.getIdentifier());
        return Optional.of(patient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> updatePatient(final UUID patientIdentifier, final Patient patient) {
        if (patients.containsKey(patientIdentifier)) {
            patients.put(patientIdentifier, patient);
            LOGGER.debug("Patient with identifier {} updated", patientIdentifier);
            return Optional.of(patient);
        }
        LOGGER.debug("Patient with identifier {} not found", patientIdentifier);
        return Optional.empty();
    }
}
