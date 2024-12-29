import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfigService } from './config.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {;

  constructor(private http: HttpClient, private config: ConfigService) {
    this.config.loadConfig();
  }

  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.config.getConfig('apiUrl')}/${endpoint}`);
  }

  post<T>(endpoint: string, data: unknown): Observable<T> {
    return this.http.post<T>(`${this.config.getConfig('apiUrl')}/${endpoint}`, data);
  }

  put<T>(endpoint: string, data: unknown): Observable<T> {
    return this.http.put<T>(`${this.config.getConfig('apiUrl')}/${endpoint}`, data);
  }

  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.config.getConfig('apiUrl')}/${endpoint}`);
  }
}
