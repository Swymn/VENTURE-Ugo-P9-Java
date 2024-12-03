package fr.swynn.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.swynn.models.Patient;
import fr.swynn.repository.PatientRepository;

@Service
public class DefaultPatientService implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public DefaultPatientService(final PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
     
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> updatePatient(final UUID patientIdentifier, final Patient patient) {
        return patientRepository.updatePatient(patientIdentifier, patient);
    } 
}