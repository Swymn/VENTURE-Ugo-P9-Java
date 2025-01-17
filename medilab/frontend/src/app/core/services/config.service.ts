import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subscription } from 'rxjs';
import { Config } from '../models/config.model';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {
  private static readonly CONFIG_ROUTE = '/assets/config.json';
  private config: Config | null;

  constructor(private http: HttpClient) {
    this.config = null;
  }

  loadConfig(): Subscription {
    return this.http.get(ConfigService.CONFIG_ROUTE).subscribe({
      next: (config) => {
        this.config = config as Config;
      },
      error: () => {
        this.config = null;
      }
    });
  }

  getConfig(key: keyof Config): unknown {
    return this.config ? this.config[key] : null;
  }
}
