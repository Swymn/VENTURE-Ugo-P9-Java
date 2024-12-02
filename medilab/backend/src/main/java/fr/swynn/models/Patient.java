package fr.swynn.models;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public record Patient(UUID identifier, String firstName, String lastName, Date birthDate, String gender, Optional<String> address, Optional<String> phoneNumber) {
    
}
