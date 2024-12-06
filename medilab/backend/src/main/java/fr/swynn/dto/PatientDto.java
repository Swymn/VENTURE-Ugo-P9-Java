package fr.swynn.dto;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientDto {
    
    @NotNull(message = "Identifier is required")
    private UUID identifier;
    
    @NotNull(message = "First name is required")
    private String firstName;
    
    @NotNull(message = "Last name is required")
    private String lastName;
    
    @NotNull(message = "Birth date is required")
    private Date birthDate;
    
    @NotNull(message = "Gender is required ('Male' or 'Female')")
    private String gender;

    private Optional<String> address; 
    private Optional<String> phoneNumber;
}
