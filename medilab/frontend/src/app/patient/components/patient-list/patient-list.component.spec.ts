import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientListComponent } from './patient-list.component';
import { PatientService } from '../../services/patient.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MockPatientFactory } from '../../mocks/FakePatientFactory';
import { PatientUpdateFormComponent } from '../patient-update-form/patient-update-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('PatientListComponent', () => {
  let component: PatientListComponent;
  let fixture: ComponentFixture<PatientListComponent>;
  let mockPatientService: Partial<PatientService>;

  beforeEach(async () => {
    mockPatientService = {
      findAllPatients: jest.fn().mockReturnValue(of([MockPatientFactory.createFakePatient('1'), MockPatientFactory.createFakePatient('2')])),
      updatePatient: (patient) => jest.fn().mockReturnValue(of(patient))()
    };

    await TestBed.configureTestingModule({
      declarations: [PatientListComponent, PatientUpdateFormComponent],
      providers: [
        { provide: PatientService, useValue: mockPatientService },
        provideHttpClient(),
        provideHttpClientTesting(),
      ],
      imports: [
        ReactiveFormsModule,
        FormsModule,
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

  test('Should update patients on form submit', async () => {
    // GIVEN a mocked response with patients
    fixture.detectChanges();

    // WHEN user submit patient update
    const patient = MockPatientFactory.createFakePatient('1');
    patient.firstName = 'Jane';
    component.onSubmit(patient);

    // THEN it should update the patient list
    expect(component.patients[0].firstName).toBe('Jane');
  });

  test('Should cancel update', async () => {
    // GIVEN a mocked response with patients
    fixture.detectChanges();

    // WHEN user cancel the update
    component.onCancelEdit();

    // THEN it should cancel the update
    expect(component.selectedPatient).toBeUndefined();
  });
});
