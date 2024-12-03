package fr.swynn.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public class FakePatientRepository implements PatientRepository {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private List<Patient> patients;

    public FakePatientRepository() {
        this.patients = new ArrayList<>(List.of(createFakePatient()));
    }

    private Patient createFakePatient() {
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Date date;
        try {
            date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        } catch (ParseException e) {
            date = new Date();
        }
        return new Patient(uuid, "John", "Doe", date, "Male", Optional.empty(), Optional.empty());
    }

    @Override
    public Optional<Patient> createPatient(final Patient patient) {
        if (patients.contains(patient)) {
            return Optional.empty();
        }
        patients.add(patient);
        return Optional.of(patient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> updatePatient(final UUID patientIdentifier, final Patient patient) {
        var patientIndex = patients.indexOf(patient);
        if (patientIndex == -1) {
            return Optional.empty();
        }
        patients.set(patientIndex, patient);
        return Optional.of(patient);
    }
}
