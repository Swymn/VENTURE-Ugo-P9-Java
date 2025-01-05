import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientUpdateFormComponent } from './patient-update-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { MockPatientFactory } from '../../mocks/FakePatientFactory';
import { By } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

describe('PatientUpdateFormComponent', () => {
  let component: PatientUpdateFormComponent;
  let fixture: ComponentFixture<PatientUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientUpdateFormComponent ],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([])
      ],
      imports: [
        ReactiveFormsModule,
        FormsModule,
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  test('should create', () => {
    expect(component).toBeTruthy();
  });

  test('should initialize form', async () => {
    // GIVEN a patient update form component
    // AND a patient
    const patient = MockPatientFactory.createFakePatient('1');
    component.patient = patient;

    // WHEN the component is initialized
    component.ngOnChanges({
      patient: {
        currentValue: patient,
        previousValue: undefined,
        firstChange: true,
        isFirstChange: () => true
      }
    });

    // THEN the form should be initialized with the patient data
    expect(component.patientForm.get('firstName')?.value).toBe('John');
    expect(component.patientForm.get('lastName')?.value).toBe('Doe');
    expect(component.patientForm.get('address')?.value).toBe('123 Main St');
    expect(component.patientForm.get('phoneNumber')?.value).toBe('5555555555');
  });

  test('should format date', () => {
    // GIVEN a date string
    const date = '2021-01-01T00:00:00';

    // WHEN the date is formatted
    const formattedDate = component.toInputDateFormat(date);

    // THEN the date should be formatted correctly
    expect(formattedDate).toBe('2021-01-01');
  });

  test('Should setup the form in the render', () => {
    // GIVEN a patient update form component
    // AND a patient
    const patient = MockPatientFactory.createFakePatient('1');
    component.patient = patient;
    fixture.detectChanges();

    // WHEN the component is initialized
    component.ngOnChanges({
      patient: {
        currentValue: patient,
        previousValue: undefined,
        firstChange: true,
        isFirstChange: () => true
      }
    });
    fixture.detectChanges();

    // THEN the form should be initialized with the patient data
    const firstNameInput = fixture.debugElement.query(By.css('input[formControlName="firstName"]')).nativeElement;
    expect(firstNameInput.value).toBe('John');

    const lastNameInput = fixture.debugElement.query(By.css('input[formControlName="lastName"]')).nativeElement;
    expect(lastNameInput.value).toBe('Doe');

    const addressInput = fixture.debugElement.query(By.css('input[formControlName="address"]')).nativeElement;
    expect(addressInput.value).toBe('123 Main St');
  });

  test('Should submit the form', () => {
    // GIVEN a patient update form component
    const submitFormSpy = jest.spyOn(component, 'onSubmitForm');
    const updatePatientSpy = jest.spyOn(component.updatePatient, 'emit');

    // AND a patient
    const patient = MockPatientFactory.createFakePatient('1');
    component.patient = patient;
    fixture.detectChanges();

    // WHEN the component is initialized
    component.ngOnChanges({
      patient: {
        currentValue: patient,
        previousValue: undefined,
        firstChange: true,
        isFirstChange: () => true
      }
    });
    fixture.detectChanges();

    // AND the form is submitted
    const submit = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    submit.click();
    fixture.detectChanges();

    // THEN the form should be validated
    expect(component.patientForm.valid).toBe(true);
    expect(submitFormSpy).toHaveBeenCalled();
    expect(updatePatientSpy).toHaveBeenCalled();
  });

  test("Should not submit the form if it's invalid", () => {
    // GIVEN a patient update form component
    const submitFormSpy = jest.spyOn(component, 'onSubmitForm');
    const updatePatientSpy = jest.spyOn(component.updatePatient, 'emit');

    // AND a patient
    const patient = MockPatientFactory.createFakePatient('1');
    component.patient = patient;
    fixture.detectChanges();

    // WHEN the component is initialized
    component.ngOnChanges({
      patient: {
        currentValue: patient,
        previousValue: undefined,
        firstChange: true,
        isFirstChange: () => true
      }
    });
    fixture.detectChanges();

    // AND the form updated
    component.patientForm.get('firstName')?.setValue('');
    fixture.detectChanges();

    // AND the form is submitted
    const submit = fixture.debugElement.query(By.css('button[type="submit"]')).nativeElement;
    submit.click();
    fixture.detectChanges();

    // THEN the form should not be validated
    expect(component.patientForm.valid).toBe(false);
    expect(component.patientForm.get('firstName')?.invalid).toBe(true);
    expect(submitFormSpy).not.toHaveBeenCalled();
    expect(updatePatientSpy).not.toHaveBeenCalled();
  });

  test("Should emit close modal event", () => {
    // GIVEN a patient update form component
    const closeSpy = jest.spyOn(component.cancelUpdate, 'emit');
    fixture.detectChanges();

    // WHEN the close button is clicked
    const close = fixture.debugElement.query(By.css('button[type="button"]')).nativeElement;
    close.click();
    fixture.detectChanges();

    // THEN it should emit the close event
    expect(closeSpy).toHaveBeenCalled();
  })
});
