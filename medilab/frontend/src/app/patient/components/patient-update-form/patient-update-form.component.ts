import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { Patient } from '../../models/patient.model';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-patient-update-form',
  templateUrl: './patient-update-form.component.html',
  styleUrls: ['./patient-update-form.component.css']
})
export class PatientUpdateFormComponent implements OnChanges {
  @Input() patient?: Patient;
  @Output() updatePatient;
  @Output() cancelUpdate;

  patientForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.patientForm = this.initializePatientForm();
    this.updatePatient = new EventEmitter<Patient>();
    this.cancelUpdate = new EventEmitter<void>();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['patient'] && changes['patient'].currentValue) {
      this.patientForm = this.initializePatientForm(changes['patient'].currentValue);
    }
  }

  onSubmitForm(): void {
    if (this.patientForm.valid) {
      this.updatePatient.emit(this.toPatient());
    }
  }

  onCancelUpdate(): void {
    this.cancelUpdate.emit();
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

  toPatient(): Patient {
    return {
      identifier: this.patient!.identifier,
      createdAt: this.patient!.createdAt,
      updatedAt: this.patient!.updatedAt,
      firstName: this.patientForm.get('firstName')?.value,
      lastName: this.patientForm.get('lastName')?.value,
      address: this.patientForm.get('address')?.value,
      phoneNumber: this.patientForm.get('phoneNumber')?.value,
      birthDate: this.patientForm.get('birthDate')?.value,
      gender: this.patient!.gender
    };
  }
}
