package fr.swynn.controllers;

import fr.swynn.dto.PatientDto;
import fr.swynn.models.Patient;

public class PublicObjectMapper {
    
    public Patient map(final PatientDto patientDto) {
        return new Patient(
            patientDto.getIdentifier(),
            patientDto.getFirstName(),
            patientDto.getLastName(),
            patientDto.getBirthDate(),
            patientDto.getGender(),
            patientDto.getAddress(),
            patientDto.getPhoneNumber()
        );
    }

}
