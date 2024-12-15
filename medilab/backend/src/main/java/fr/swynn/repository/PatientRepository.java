package fr.swynn.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.swynn.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

}
