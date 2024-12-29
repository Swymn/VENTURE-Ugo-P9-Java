import { TestBed } from '@angular/core/testing';

import { PatientService } from './patient.service';
import { ApiService } from '../../core/services/api.service';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { Patient } from '../models/patient.model';
import { of, Observable } from 'rxjs';

describe('PatientService', () => {
  let service: PatientService;
  let httpMock: HttpTestingController;
  let apiService: ApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PatientService,
        ApiService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(PatientService);
    httpMock = TestBed.inject(HttpTestingController);
    apiService = TestBed.inject(ApiService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  function createFakePatient(identifier: string): Patient {
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

  test('should be created', () => {
    // GIVEN a patient service
    // WHEN it is created
    // THEN it should be truthy
    expect(service).toBeTruthy();
  });

  test('should have a findAllPatients method', () => {
    // GIVEN a patient service
    // WHEN it is created
    // THEN it should have a findAllPatients method
    expect(service.findAllPatients).toBeDefined();
  });

  test('should return an Observable of Patient[]', () => {
    // GIVEN a patient service
    // WHEN the findAllPatients method is called
    const patients = service.findAllPatients();

    // THEN it should return an Observable of Patient[]
    expect(patients).toBeDefined();
    expect(patients).toBeInstanceOf(Observable);
  });

  test('should return an Observable of Patient[] with 2 patients', () => {
    // GIVEN a patient service
    // AND a mocked response with 2 patients
    const mockedPatients: Patient[] = [createFakePatient('1'), createFakePatient('2')];
    jest.spyOn(apiService, 'get').mockReturnValue(of(mockedPatients));

    // WHEN the findAllPatients method is called
    const patients = service.findAllPatients();

    // THEN it should return an Observable of Patient[] with 2 patients
    patients.subscribe({
      next: (data) => {
        expect(data.length).toBe(2);
        expect(data).toEqual(mockedPatients);
      },
      error: () => fail('expected patients, not error!')
    });
  });

  test('Should return an Observable with an error', () => {
    // GIVEN a patient service
    // AND a mocked response with an error
    const error = new Error('An error occurred');
    jest.spyOn(apiService, 'get').mockReturnValue(of(error));

    // WHEN the findAllPatients method is called
    const patients = service.findAllPatients();

    // THEN it should return an Observable with an error
    patients.subscribe({
      next: () => fail('expected error, not patients!'),
      error: (err) => {
        expect(err).toEqual(error);
      }
    });
  });
});
