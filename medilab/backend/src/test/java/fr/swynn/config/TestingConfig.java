package fr.swynn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import fr.swynn.repository.FakePatientRepository;
import fr.swynn.repository.PatientRepository;

@Configuration
@Profile("testing")
public class TestingConfig {
    
    @Bean
    public PatientRepository patientRepository() {
        return new FakePatientRepository();
    }
}
