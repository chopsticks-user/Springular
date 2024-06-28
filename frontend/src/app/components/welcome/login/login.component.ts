import { Component, OnInit, inject } from '@angular/core';
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
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  public hidePassword = true;
  public loginStatus: string = '';
  public loginFormGroup: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
    ]),
  });

  private _authService = inject(AuthService);
  private _router = inject(Router);
  private _location = inject(Location);

  public constructor() {
    this._location.go('/login');
  }

  public ngOnInit(): void {}

  public get email() {
    return this.loginFormGroup.get('email');
  }

  public get emailInvalid() {
    return this.email?.invalid && (this.email?.dirty || this.email?.touched);
  }

  public get password() {
    return this.loginFormGroup.get('password');
  }

  public get passwordInvalid() {
    return (
      this.password?.invalid && (this.password?.dirty || this.password?.touched)
    );
  }

  public loginHandler() {
    if (this.loginFormGroup.invalid) {
      this.loginFormGroup.markAllAsTouched();
      return;
    }

    const loginInfo: LoginInfo = {
      email: this.email?.value,
      password: this.password?.value,
    };

    this._authService.authenticate(loginInfo).subscribe({
      next: () => this._router.navigateByUrl('/home'),
      error: (res: HttpErrorResponse) =>
        // (this.loginStatus = JSON.parse(error.error).description as string),
        (this.loginStatus = res.error.description),
    });
  }
}
