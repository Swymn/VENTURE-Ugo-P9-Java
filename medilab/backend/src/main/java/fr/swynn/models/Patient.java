package fr.swynn.models;

import java.util.Date;
import java.util.UUID;

public record Patient(UUID identifier, String firstName, String lastName, Date birthDate, String gender, String address, String phoneNumber) {
    
}
