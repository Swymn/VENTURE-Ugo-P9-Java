import { APP_INITIALIZER, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { PatientRoutingModule } from './patient-routing.module';
import { ConfigService } from '../core/services/config.service';
import { Subscription } from 'rxjs';
import { PatientUpdateFormComponent } from './components/patient-update-form/patient-update-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CoreModule } from '../core/core.module';

export function initializePatientModule(configService: ConfigService): () => Subscription {
  return () => configService.loadConfig();
}

@NgModule({
  declarations: [
    PatientListComponent,
    PatientUpdateFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CoreModule,
    PatientRoutingModule
  ],
  providers: [
    ConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializePatientModule,
      deps: [ConfigService],
      multi: true
    }
  ],
  exports: [
    PatientListComponent,
    PatientUpdateFormComponent
  ]
})
export class PatientModule { }
