package fr.swynn.models;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Patient")
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull(message = "Identifier is required")
    private UUID identifier;

    @Column(updatable = false, nullable = false)
    @NotNull(message = "Creation date is required")
    private Date createdAt;

    @Column(nullable = false)
    @NotNull(message = "Update date is required")
    private Date updatedAt;

    @Column(nullable = false)
    @NotNull(message = "First name is required")
    private String firstName;
    
    @Column(nullable = false)
    @NotNull(message = "Last name is required")
    private String lastName;

    @Column(nullable = false)
    @NotNull(message = "Birth date is required")
    private Date birthDate;

    @Column(nullable = false)
    @NotNull(message = "Gender is required (Male/Female)")
    private String gender;

    private String address;
    private String phoneNumber;
}
