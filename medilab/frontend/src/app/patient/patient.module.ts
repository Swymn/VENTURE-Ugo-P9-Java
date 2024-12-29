import { APP_INITIALIZER, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { PatientRoutingModule } from './patient-routing.module';
import { ConfigService } from '../core/services/config.service';
import { Subscription } from 'rxjs';

export function initializePatientModule(configService: ConfigService): () => Subscription {
  return () => configService.loadConfig();
}

@NgModule({
  declarations: [
    PatientListComponent
  ],
  imports: [
    CommonModule,
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
    PatientListComponent
  ]
})
export class PatientModule { }
