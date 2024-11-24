package fr.swynn.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public class FakePatientService implements PatientService {

    private final List<Patient> patients;

    public FakePatientService() {
        var patient1 = new Patient(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "John", "Doe", new Date(), "Male", "123 Main Street, Springfield, IL 62701, USA", "+1-555-123-4567");
        patients = List.of(patient1);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> updatePatientFirstName(final UUID patientIdentifier, final String newFirstName) {
        Optional<Patient> optionalPatient = patients.stream()
                .filter(p -> p.identifier().equals(patientIdentifier))
                .findFirst();
        
        if (optionalPatient.isEmpty()) {
            return Optional.empty();
        }
        
        var patient = optionalPatient.get();
        var updatedPatient = new Patient(patient.identifier(), newFirstName, patient.lastName(), patient.birthDate(), patient.gender(), patient.address(), patient.phoneNumber());
        return Optional.of(updatedPatient);
    }
}
