package fr.swynn.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.swynn.models.Patient;

public class FakePatientService implements PatientService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final List<Patient> patients;

    public FakePatientService() {
        patients = new ArrayList<>(List.of(createFakePatient()));
    }

    private Patient createFakePatient() {
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Date date;
        try {
            date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        } catch (ParseException e) {
            date = new Date();
        }
        var creationDate = new Date();
        return new Patient(uuid, creationDate, creationDate, "John", "Doe", date, "Male", Optional.empty(), Optional.empty());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Patient> updatePatient(final UUID patientIdentifier, final Patient patient) {
        for (var i = 0; i < patients.size(); i++) {
            var currentPatient = patients.get(i);
            if (currentPatient.getIdentifier().equals(patientIdentifier)) {
                patients.set(i, patient);
                return Optional.of(patient);
            }
        }

        return Optional.empty();
    }
}
