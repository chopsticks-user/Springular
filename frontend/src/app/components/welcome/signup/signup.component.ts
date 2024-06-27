import { Component, OnInit, inject } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { SignupInfo } from '@shared/types';
import { AuthService } from '@services/auth.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatButtonModule,
  ],
  providers: [],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent implements OnInit {
  public hidePassword = true;
  public signupStatus: string = '';
  public signupFormGroup: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    dateOfBirth: new FormControl('', [Validators.required]), // TODO: custom validator
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
    this._location.go('/signup');
  }

  public ngOnInit(): void {}

  // TODO: verify input validity
  public get firstName() {
    return this.signupFormGroup.get('firstName');
  }

  public get firstNameInvalid() {
    return (
      this.firstName?.invalid &&
      (this.firstName?.dirty || this.firstName?.touched)
    );
  }

  public get lastName() {
    return this.signupFormGroup.get('lastName');
  }

  public get lastNameInvalid() {
    return (
      this.lastName?.invalid && (this.lastName?.dirty || this.lastName?.touched)
    );
  }

  public get dateOfBirth() {
    return this.signupFormGroup.get('dateOfBirth');
  }

  public get dateOfBirthInvalid() {
    return (
      this.dateOfBirth?.invalid &&
      (this.dateOfBirth?.dirty || this.dateOfBirth?.touched)
    );
  }

  public get email() {
    return this.signupFormGroup.get('email');
  }

  public get emailInvalid() {
    return this.email?.invalid && (this.email?.dirty || this.email?.touched);
  }

  public get password() {
    return this.signupFormGroup.get('password');
  }

  public get passwordInvalid() {
    return (
      this.password?.invalid && (this.password?.dirty || this.password?.touched)
    );
  }

  public signupHandler() {
    if (this.signupFormGroup.invalid) {
      this.signupFormGroup.markAllAsTouched();
      return;
    }

    const signupInfo: SignupInfo = {
      firstName: this.firstName?.value,
      lastName: this.lastName?.value,
      dateOfBirth: this.dateOfBirth?.value,
      email: this.email?.value,
      password: this.password?.value,
    };

    this._authService.register(signupInfo).subscribe({
      next: () => {
        this._authService
          .authenticate({
            email: signupInfo.email,
            password: signupInfo.password,
          })
          .subscribe({
            next: () => this._router.navigateByUrl('/home'),
            error: (error: HttpErrorResponse) =>
              (this.signupStatus = JSON.parse(error.error)
                .description as string),
          });
      },
      error: (error: HttpErrorResponse) =>
        (this.signupStatus = JSON.parse(error.error).description as string),
    });
  }
}
