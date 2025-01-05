import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientListComponent } from './patient-list.component';
import { PatientService } from '../../services/patient.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MockPatientFactory } from '../../mocks/FakePatientFactory';

describe('PatientListComponent', () => {
  let component: PatientListComponent;
  let fixture: ComponentFixture<PatientListComponent>;
  let mockPatientService: Partial<PatientService>;

  beforeEach(async () => {
    mockPatientService = {
      findAllPatients: jest.fn().mockReturnValue(of([MockPatientFactory.createFakePatient('1'), MockPatientFactory.createFakePatient('2')]))
    };

    await TestBed.configureTestingModule({
      declarations: [PatientListComponent],
      providers: [
        { provide: PatientService, useValue: mockPatientService },
        provideHttpClient(),
        provideHttpClientTesting(),
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
    expect(component.patients).toEqual([MockPatientFactory.createFakePatient('1'), MockPatientFactory.createFakePatient('2')]);
  });

  test('ngOnInit should render patients in the template', async () => {
    // GIVEN a mocked response with patients
    // WHEN ngOnInit is called
    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();

    // THEN it should render the patients in the template
    const compiled = fixture.nativeElement;
    expect(compiled.querySelectorAll('tr').length).toBe(3); // 1 header + 2 patients
  });

  test('Should defined selected user', async () => {
    // GIVEN a mocked response with patients
    fixture.detectChanges();

    // WHEN user clicks on a patient
    const patientLink = fixture.debugElement.queryAll((element) => element.name === 'button')[0];
    patientLink.nativeElement.click();
    fixture.detectChanges();

    // THEN it should navigate to the update form when clicking on a patient
    expect(component.selectedPatient).toBeDefined();
  });
});
