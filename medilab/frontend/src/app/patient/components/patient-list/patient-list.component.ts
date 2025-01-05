import { Component, OnInit } from '@angular/core';
import { Patient } from '../../models/patient.model';
import { PatientService } from '../../services/patient.service';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrl: './patient-list.component.css'
})
export class PatientListComponent implements OnInit {
  patients: Patient[] = [];
  selectedPatient: Patient | undefined;
  error: string | undefined = undefined;

  constructor(private service: PatientService) { }

  ngOnInit() {
    this.service.findAllPatients().subscribe({
      next: (patients) => this.patients = patients,
      error: () => this.error = `Une erreur est survenu, veuillez réessayer plus tard.`
    });
  }

  onEdit(patient: Patient) {
    this.selectedPatient = patient;
  }
}
