import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'patients',
    loadChildren: () => import('./patient/patient.module').then(m => m.PatientModule)
  },
  {
    path: '',
    redirectTo: '/patients',
    pathMatch: 'full'
  }
];
