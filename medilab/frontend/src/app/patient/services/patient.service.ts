import { Injectable } from '@angular/core';
import { ApiService } from '../../core/services/api.service';
import { Observable } from 'rxjs';
import { Patient } from '../models/patient.model';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private apiService: ApiService) { }

  /**
   * Fetches all patients from the API.
   * @returns {Observable<Patient[]>} An observable of the list of patients.
   */
  findAllPatients(): Observable<Patient[]> {
    return this.apiService.get<Patient[]>('patients');
  }

  /**
   * Update a patient.
   * @param {Patient} patient The patient to update.
   * @returns {Observable<Patient>} An observable of the updated patient.
   */
  updatePatient(patient: Patient): Observable<Patient> {
    return this.apiService.put<Patient>(`patients/${patient.identifier}`, patient);
  }
}
