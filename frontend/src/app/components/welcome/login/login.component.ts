import { Component, OnInit, inject, model, signal } from '@angular/core';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import {
  FormControl,
  FormGroup,
  FormsModule,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { AuthService } from '@services/auth.service';
import { LoginInfo } from '@shared/types';
import { NgIf } from '@angular/common';
import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatIconModule,
    NgIf,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  hidePassword = true;

  loginStatus!: string;

  authService = inject(AuthService);

  router = inject(Router);

  location = inject(Location);

  login!: FormGroup;

  ngOnInit(): void {
    this.login = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.required]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });
    this.location.go('/login');
  }

  get email() {
    return this.login.get('email');
  }

  get emailInvalid() {
    return this.email?.invalid && (this.email?.dirty || this.email?.touched);
  }

  get password() {
    return this.login.get('password');
  }

  get passwordInvalid() {
    return (
      this.password?.invalid && (this.password?.dirty || this.password?.touched)
    );
  }

  loginHandler() {
    if (this.login.invalid) {
      this.login.markAllAsTouched();
      return;
    }

    const loginInfo: LoginInfo = {
      email: this.email?.value,
      password: this.password?.value,
    };

    this.authService.authenticate(loginInfo).subscribe({
      next: () => this.router.navigateByUrl('/home'),
      error: (error: HttpErrorResponse) =>
        (this.loginStatus = JSON.parse(error.error).description as string),
    });
  }
}
