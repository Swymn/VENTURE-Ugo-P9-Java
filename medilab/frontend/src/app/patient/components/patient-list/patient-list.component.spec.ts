import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientListComponent } from './patient-list.component';
import { PatientService } from '../../services/patient.service';
import { of } from 'rxjs';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MockPatientFactory } from '../../mocks/FakePatientFactory';
import { provideRouter, RouterModule } from '@angular/router';
import { PatientUpdateFormComponent } from '../patient-update-form/patient-update-form.component';
import { Location } from '@angular/common';

describe('PatientListComponent', () => {
  let component: PatientListComponent;
  let fixture: ComponentFixture<PatientListComponent>;
  let mockPatientService: Partial<PatientService>;
  let location: Location;

  beforeEach(async () => {
    mockPatientService = {
      findAllPatients: jest.fn().mockReturnValue(of([MockPatientFactory.createFakePatient('1'), MockPatientFactory.createFakePatient('2')]))
    };

    await TestBed.configureTestingModule({
      declarations: [PatientListComponent],
      imports: [RouterModule],
      providers: [
        { provide: PatientService, useValue: mockPatientService },
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([{ path: 'patients/update/:id', component: PatientUpdateFormComponent }])
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientListComponent);
    component = fixture.componentInstance;
    location = TestBed.inject(Location);
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

  test('Should navigate to the update form when clicking on a patient', async () => {
    // GIVEN a mocked response with patients
    fixture.detectChanges();

    // WHEN user clicks on a patient
    const patientLink = fixture.debugElement.queryAll((element) => element.name === 'a')[0];
    patientLink.nativeElement.click();
    fixture.detectChanges();

    // THEN it should navigate to the update form when clicking on a patient
    expect(location.path()).toBe('/patients/update/1');
  });
});
