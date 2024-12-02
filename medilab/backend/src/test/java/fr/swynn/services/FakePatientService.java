package fr.swynn.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public class FakePatientService implements PatientService {

    private final List<Patient> patients;

    public FakePatientService() {
        var patient1 = new Patient(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "John", "Doe", new Date(), "Male", Optional.of("123 Main Street, Springfield, IL 62701, USA"), Optional.of("+1-555-123-4567"));
        patients = new ArrayList<>(List.of(patient1));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> updatePatient(final UUID patientIdentifier, final Patient patient) {
        for (var i = 0; i < patients.size(); i++) {
            var currentPatient = patients.get(i);
            if (currentPatient.identifier().equals(patientIdentifier)) {
                patients.set(i, patient);
                return Optional.of(patient);
            }
        }

        return Optional.empty();
    }
}
