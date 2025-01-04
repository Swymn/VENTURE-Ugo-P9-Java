import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientUpdateFormComponent } from './patient-update-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';

describe('PatientUpdateFormComponent', () => {
  let component: PatientUpdateFormComponent;
  let fixture: ComponentFixture<PatientUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        PatientUpdateFormComponent,
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([])
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatientUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
