import { Patient } from "../models/patient.model";

export class MockPatientFactory {

  static createFakePatient(identifier: string): Patient {
    return {
      identifier,
      createdAt: '2024-01-01',
      updatedAt: '2024-01-01',
      firstName: 'John',
      lastName: 'Doe',
      birthDate: '1980-01-01T00:00:00.000Z',
      address: '123 Main St',
      phoneNumber: '5555555555',
      gender: 'Male'
    }
  }
}
