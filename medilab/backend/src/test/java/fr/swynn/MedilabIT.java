package fr.swynn;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import fr.swynn.models.Patient;
import fr.swynn.repository.PatientRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MedilabIT {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static PatientRepository patientRepository;

    @LocalServerPort
    private int port;
    
    private HttpClient client;

    @BeforeAll
    static void setUpAll() {
        MAPPER.registerModule(new Jdk8Module());
        patientRepository = Mockito.mock(PatientRepository.class);
        Mockito.when(patientRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);
    }

    @BeforeEach
    void setUp() {
        client = HttpClient.newHttpClient();
    }

    private static String createFakeParsedPatient(final UUID uuid) throws ParseException, IOException {
        return createFakeParsedPatient(uuid, "John");
    }

    private static String createFakeParsedPatient(final UUID uuid, final String firstName) throws ParseException, IOException {
        var date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        var creationDate = new Date();
        var patient = new Patient(uuid, creationDate, creationDate, firstName, "Doe", date, "Male", null, null);
        return MAPPER.writeValueAsString(patient);
    }

    private HttpRequest buildRequest(final UUID patientUUid, final String parsedPatient) {
        var baseUrl = "http://localhost:" + port + "/patients/" + patientUUid;

        return HttpRequest.newBuilder()
            .uri(URI.create(baseUrl))
            .header("Content-Type", "application/json")
            .method("PUT", HttpRequest.BodyPublishers.ofString(parsedPatient))
            .build();
    }

    @Test
    void patchPatient_updatedPatient_existingPatient() throws ParseException, IOException, InterruptedException {
        // GIVEN an http client
        // AND a patient with a known UUID
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakeParsedPatient(uuid);

        // WHEN we send a PATCH request to /patients/id
        var request = buildRequest(uuid, patient);

        // THEN the response should be 200
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void patchPatient_updatedPatientFirstName_existingPatient() throws ParseException, IOException, InterruptedException {
        // GIVEN an http client
        // AND a patient with a known UUID
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var newPatientFirstName = "Jane";
        var patient = createFakeParsedPatient(uuid, newPatientFirstName);
        
        // WHEN we send a PATCH request to /patients/id
        var request = buildRequest(uuid, patient);

        // THEN the patient's first name should be updated
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var updatedPatient = MAPPER.readValue(response.body(), Patient.class);

        Assertions.assertEquals(newPatientFirstName, updatedPatient.getFirstName());
    }

    @Test
    void patchPatient_payloadMissinField_exisitingPatient() throws IOException, InterruptedException {
        // GIVEN an http client
        // AND a patient with a known UUID
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = "{\"identifier\":\"550e8400-e29b-41d4-a716-446655440000\",\"createdAt\":\"2023-10-01T00:00:00Z\",\"updatedAt\":\"2023-10-01T00:00:00Z\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthDate\":\"2023-10-01T00:00:00Z\",\"gender\":\"Male\"}";

        // WHEN we send a PATCH request to /patients/id
        var request = buildRequest(uuid, patient);

        // THEN the response should be 200
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    void patchPatient_updatedPatientWithMissingField_exisitingPatient() throws IOException, InterruptedException {
        // GIVEN an http client
        // AND a patient with a known UUID
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = "{\"identifier\":\"550e8400-e29b-41d4-a716-446655440000\",\"createdAt\":\"2023-10-01T00:00:00Z\",\"updatedAt\":\"2023-10-01T00:00:00Z\",\"firstName\":\"John\",\"lastName\":\"Dane\",\"birthDate\":\"2023-10-01T00:00:00Z\",\"gender\":\"Male\"}";

        // WHEN we send a PATCH request to /patients/id
        var request = buildRequest(uuid, patient);

        // THEN the response should be 200
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var updatedPatient = MAPPER.readValue(response.body(), Patient.class);
        Assertions.assertEquals("Dane", updatedPatient.getLastName());
    }

    @Test
    void patchPatient_emptyPatient_missingPatient() throws ParseException, IOException, InterruptedException {
        // GIVEN an http client
        // AND a patient with a known UUID
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        var patient = createFakeParsedPatient(uuid);

        // WHEN we send a PATCH request to /patients/id
        var request = buildRequest(uuid, patient);

        // THEN the response should be 404
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    void patchPatient_badRequestError_emptyPayload() throws IOException , InterruptedException {
        // GIVEN an http client
        // AND an empty payload
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var emptyPatientPayload = "{}";

        // WHEN we send a PATCH request to /patients/id
        var request = buildRequest(uuid, emptyPatientPayload);

        // THEN the response should be 400
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(400, response.statusCode());
    }
}
