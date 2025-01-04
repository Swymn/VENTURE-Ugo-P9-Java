import { Patient } from "../models/patient.model";

export class MockPatientFactory {

  static createFakePatient(identifier: string): Patient {
    return {
      identifier,
      createdAt: '2024-01-01',
      updatedAt: '2024-01-01',
      firstName: 'John',
      lastName: 'Doe',
      birthDate: '1980-01-01',
      address: '123 Main St',
      phoneNumber: '555-555-5555',
      gender: 'Male'
    }
  }
}