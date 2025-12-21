// src/app/features/auth/pages/register/register.ts
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthApi, CreateReporterRequest, ApiResponse, AuthResponse } from '../../../auth/data-access/auth.api';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  private authApi = inject(AuthApi);

  // form model bound via [(ngModel)] in register.html
  form: CreateReporterRequest = {
    firstName: '',
    lastName: '',
    phone: '',
    email: '',
    password: '',
  };

  isSubmitting = false;
  errorMessage: string | null = null;

  register(): void {
    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = null;

    this.authApi.register(this.form).subscribe({
      next: (response: ApiResponse<AuthResponse>) => {
        this.isSubmitting = false;

        if (response.success && response.data) {
          console.log('Registration successful:', response);

          // TODO:
          // - store response.data.token with a TokenService
          // - maybe navigate to another page
        } else {
          this.errorMessage = response.message || 'Registration failed';
        }
      },
      error: (error) => {
        this.isSubmitting = false;
        console.error('Registration failed:', error);
        this.errorMessage = 'Registration failed. Please try again.';
      },
    });
  }
}
