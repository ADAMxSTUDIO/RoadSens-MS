import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  AuthApi,
  LoginRequest,
  ApiResponse,
  AuthResponse,
} from '../../../../features/auth/data-access/auth.api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  private authApi = inject(AuthApi);

  form: LoginRequest = {
    email: '',
    password: '',
  };

  isSubmitting = false;
  errorMessage: string | null = null;

  login(): void {
    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = null;

    this.authApi.login(this.form).subscribe({
      next: (response: ApiResponse<AuthResponse>) => {
        this.isSubmitting = false;

        if (response.success && response.data) {
          console.log('Login successful:', response);

          // TODO:
          // - store response.data.token in a TokenService
          // - navigate to a protected page
        } else {
          this.errorMessage = response.message || 'Login failed';
        }
      },
      error: (error) => {
        this.isSubmitting = false;
        console.error('Login failed:', error);
        this.errorMessage = 'Login failed. Please check your credentials.';
      },
    });
  }
}
