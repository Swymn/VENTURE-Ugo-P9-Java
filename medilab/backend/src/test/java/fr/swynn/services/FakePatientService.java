package fr.swynn.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

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
        return updatePatient(patientIdentifier, patient -> 
            new Patient(patient.identifier(), newFirstName, patient.lastName(), patient.birthDate(), patient.gender(), patient.address(), patient.phoneNumber()));
    }

    @Override
    public Optional<Patient> addPostalAddress(final UUID patientIdentifier, final String address) {
        return updatePatient(patientIdentifier, patient -> 
            new Patient(patient.identifier(), patient.firstName(), patient.lastName(), patient.birthDate(), patient.gender(), address, patient.phoneNumber()));
    }

    private Optional<Patient> updatePatient(UUID patientIdentifier, Function<Patient, Patient> updater) {
        return patients.stream()
                .filter(p -> p.identifier().equals(patientIdentifier))
                .findFirst()
                .map(updater);
    }
}
