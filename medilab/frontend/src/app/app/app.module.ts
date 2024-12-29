import { NgModule, provideZoneChangeDetection } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { HomeComponent } from "./home/home.component";
import { CoreModule } from "../core/core.module";
import { AppRoutingModule } from "./app-routing.module";
import { PatientModule } from "../patient/patient.module";

@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    PatientModule,
  ],
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true })
  ],
  bootstrap: [HomeComponent]
})
export class AppModule { }
