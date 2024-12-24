import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientListComponent } from './patient-list.component';
import { Patient } from '../../models/patient.model';
import { PatientService } from '../../services/patient.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('PatientListComponent', () => {
  let component: PatientListComponent;
  let fixture: ComponentFixture<PatientListComponent>;
  let mockPatientService: Partial<PatientService>;

  beforeEach(async () => {
    mockPatientService = {
      findAllPatients: jest.fn().mockReturnValue(of([{}, {}]))
    };

    await TestBed.configureTestingModule({
      declarations: [PatientListComponent],
      providers: [
        { provide: PatientService, useValue: mockPatientService },
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  test('should create', () => {
    expect(component).toBeTruthy();
  });

  test('ngOnInit should call findAllPatients and set patients', () => {
    // GIVEN a mocked response with patients
    // WHEN ngOnInit is called
    fixture.detectChanges();

    // THEN it should set the patients list with the mocked patients
    expect(component.patients).toEqual([{}, {}]);
  });

  test('ngOnInit should render patients in the template', async () => {
    // GIVEN a mocked response with patients
    // WHEN ngOnInit is called
    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();

    // THEN it should render the patients in the template
    const compiled = fixture.nativeElement;
    expect(compiled.querySelectorAll('li').length).toBe(2);
  });
});


/**
 * // GIVEN a patient list component
    // AND a mocked list of 2 patients
    const mockedPatients: Patient[] = [{}, {}];

    // WHEN the component is created
    jest.spyOn(patientService, 'findAllPatients').mockReturnValue(of(mockedPatients));
    fixture.detectChanges();

    // THEN it should have a patients list with 2 patients
    expect(component.patients).toBeDefined();
    expect(component.patients.length).toBe(2);
    // AND it should be rendered in the template
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('li')).toBeDefined();
    expect(compiled.querySelectorAll('li').length).toBe(2);
 */
