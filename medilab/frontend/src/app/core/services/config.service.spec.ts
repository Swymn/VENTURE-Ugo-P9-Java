import { TestBed } from '@angular/core/testing';

import { ConfigService } from './config.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

describe('ConfigService', () => {
  let service: ConfigService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ConfigService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });
    service = TestBed.inject(ConfigService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  })

  test('should be created', () => {
    expect(service).toBeTruthy();
  });

  test('should call the right url', async () => {
    // GIVEN a configuration service
    // AND a mock configuration
    const mockConfig = {
      apiUrl: 'http://localhost:8080',
    }

    // WHEN the configuration is loaded
    service.loadConfig();

    // THEN the configuration should be loaded
    const req = httpMock.expectOne('/assets/config.json');
    expect(req.request.method).toBe('GET');
    req.flush(mockConfig);

    // AND the configuration should be set
    expect(service.getConfig('apiUrl')).toBe('http://localhost:8080');
  });
});
