import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Patient } from '../../models/patient.model';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-patient-update-form',
  templateUrl: './patient-update-form.component.html',
  styleUrls: ['./patient-update-form.component.css']
})
export class PatientUpdateFormComponent implements OnChanges {
  @Input() patient: Patient | undefined;
  patientForm: FormGroup = this.initializePatientForm();

  constructor(private formBuilder: FormBuilder) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patient'] && changes['patient'].currentValue) {
      this.patientForm = this.initializePatientForm(changes['patient'].currentValue);
    }
  }

  onSubmit(): void {
    if (this.patientForm.valid) {
      console.log('Form is valid');
    } else {
      console.log('Form is invalid');
    }
  }

  initializePatientForm(patient?: Patient): FormGroup {
    return this.formBuilder.group({
      firstName: new FormControl(patient?.firstName, [Validators.required]),
      lastName: new FormControl(patient?.lastName, [Validators.required]),
      address: new FormControl(patient?.address, []),
      phoneNumber: new FormControl(patient?.phoneNumber, [Validators.pattern(/^\d+$/)]),
      gender: new FormControl(patient?.gender, [Validators.required]),
      birthDate: new FormControl(patient?.birthDate && this.toInputDateFormat(patient.birthDate), [Validators.required]),
    });
  }

  toInputDateFormat(date: string): string {
    return date.split('T')[0];
  }
}
