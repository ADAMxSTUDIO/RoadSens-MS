import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment.prod';

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T | null;
  timestamp: string; // LocalDateTime serialized as string
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface CreateReporterRequest {
  firstName: string;
  lastName: string;
  phone: string;
  email: string;
  password: string;
}

export interface ReporterDto extends CreateReporterRequest {
  id: number;
}

export interface AuthResponse {
  token: string;
  reporter: ReporterDto;
}

@Injectable({ providedIn: 'root' })
export class AuthApi {
    private http = inject(HttpClient);
    private readonly backendUrl = environment.url;
    private baseUrl = `${this.backendUrl}/ingestion/auth`;

    register(payload: CreateReporterRequest): Observable<ApiResponse<AuthResponse>> {
      return this.http.post<ApiResponse<AuthResponse>>(`${this.baseUrl}/register`, payload);
    }

    login(payload: LoginRequest): Observable<ApiResponse<AuthResponse>> {
      return this.http.post<ApiResponse<AuthResponse>>(`${this.baseUrl}/login`, payload);
    }
}
