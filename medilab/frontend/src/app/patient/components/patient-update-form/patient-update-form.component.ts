import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-patient-update-form',
  templateUrl: './patient-update-form.component.html',
  styleUrl: './patient-update-form.component.css'
})
export class PatientUpdateFormComponent {
  constructor(private route: ActivatedRoute) {
    this.route.params.subscribe((params) => {
      const patientIdentifier = params['patientIdentifier'];
      console.log(`Patient identifier: ${patientIdentifier}`);
    });
  }
}
