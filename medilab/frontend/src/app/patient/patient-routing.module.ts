import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { PatientUpdateFormComponent } from './components/patient-update-form/patient-update-form.component';

const routes: Routes = [
  { path: '', component: PatientListComponent },
  { path: 'update/:patientIdentifier', component: PatientUpdateFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
