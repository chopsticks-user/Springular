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
import { LoginService } from '../../../services/login.service';
import { LoginInfo } from '../../../models/loginInfo';
import { NgIf } from '@angular/common';

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

  loginService = inject(LoginService);

  router = inject(Router);

  login!: FormGroup;

  ngOnInit(): void {
    this.login = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.required]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });
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

    this.loginService.authenticate(
      loginInfo,
      () => this.router.navigateByUrl('/home'), // Todo: replace with a home service
      (errMesg) => (this.loginStatus = errMesg as string)
    );
  }
}