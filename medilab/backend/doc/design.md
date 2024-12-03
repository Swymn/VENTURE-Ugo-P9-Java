# Medilab - Backend design documentation

```mermaid
classDiagram
    class PatientController {
        + updatePatient(patientIdentifier: UUID, patient: Patient)
    }

    class PatientService {
        + updatePatient(patientIdentifier: UUID, patient: Patient)
    }

    class PatientRepository {
        + createPatient(patient: Patient)
        + updatePatient(identifier: UUID, patient: Patient)
    }

    class Patient {
        - UUID identifier
        - String firstName
        - String lastName
        - Date birthDate
        - String gender
        - Optional<\String> address
        - Optional<\String> phoneNumber
    }

    PatientController --> PatientService
    PatientService --> PatientRepository

    PatientController ..> Patient
    PatientService ..> Patient
    PatientRepository ..> Patient

```