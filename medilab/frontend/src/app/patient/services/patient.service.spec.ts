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
    const mockedPatients: Patient[] = [{}, {}];
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
