import { Component, OnInit } from '@angular/core';
import { Patient } from '../../models/patient.model';
import { PatientService } from '../../services/patient.service';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrl: './patient-list.component.css'
})
export class PatientListComponent implements OnInit {
  patients: Patient[];
  selectedPatient?: Patient;
  error?: string;

  constructor(private patientService: PatientService) {
    this.patients = [];
  }

  ngOnInit() {
    this.loadPatients();
  }

  onEdit(patient: Patient) {
    this.selectedPatient = patient;
  }

  onCancelEdit() {
    this.selectedPatient = undefined;
  }

  onSubmit(patient: Patient) {
    this.updatePatient(patient);
  }

  private loadPatients(): void {
    this.patientService.findAllPatients().subscribe({
      next: (patients) => this.patients = patients,
      error: () => this.handleError('Something went wrong, please try again later.')
    });
  }

  private updatePatient(patient: Patient): void {
    this.patientService.updatePatient(patient).subscribe({
      next: (updatedPatient) => {
        this.selectedPatient = undefined;
        this.patients = this.patients.map(p => p.identifier === updatedPatient.identifier ? updatedPatient : p);
      },
      error: () => this.handleError('Something went wrong, please try again later.')
    });
  }

  private handleError(message: string): void {
    this.error = message;
  }
}
