import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { PatientRoutingModule } from './patient-routing.module';

@NgModule({
  declarations: [
    PatientListComponent
  ],
  imports: [
    CommonModule,
    PatientRoutingModule
  ],
  exports: [
    PatientListComponent
  ]
})
export class PatientModule { }
