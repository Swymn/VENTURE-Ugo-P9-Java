package fr.swynn;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import fr.swynn.models.Patient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MedilabIT {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @LocalServerPort
    private int port;
    
    private HttpClient client;

    @BeforeAll
    static void setUpAll() {
        MAPPER.registerModule(new Jdk8Module());
    }

    @BeforeEach
    void setUp() {
        client = HttpClient.newHttpClient();
    }

    private Patient createFakePatient(final UUID uuid) throws ParseException {
        var date = DATE_FORMAT.parse("2023-10-01T00:00:00Z");
        return new Patient(uuid, "John", "Doe", date, "Male", Optional.empty(), Optional.empty());
    }

    @Test
    void patchPatient_emptyPatient_missingPatient() throws ParseException, IOException, InterruptedException {
        // GIVEN an http client
        // WHEN we send a PATCH request to /patients/id
        var uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var patient = createFakePatient(uuid);
        var parsedPatient = MAPPER.writeValueAsString(patient);
        var baseUrl = "http://localhost:" + port + "/patients/" + uuid;

        var request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl))
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(parsedPatient))
            .build();

        // THEN the response should be 404
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }
}
