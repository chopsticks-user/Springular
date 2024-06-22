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
import { MatDatepickerModule } from '@angular/material/datepicker';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { SignupInfo } from '../../../models/signupInfo';
import { SignupService } from '../../../services/signup.service';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatButtonModule,
    NgIf,
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent implements OnInit {
  signupService = inject(SignupService);

  loginService = inject(LoginService);

  signupStatus!: string;

  router = inject(Router);

  hide = true;

  signup!: FormGroup;

  ngOnInit(): void {
    this.signup = new FormGroup({
      firstName: new FormControl('', [Validators.required]),
      lastName: new FormControl('', [Validators.required]),
      dateOfBirth: new FormControl('', [Validators.required]), // TODO: custom validator
      email: new FormControl('', [Validators.email, Validators.required]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });
  }

  // TODO: verify input validity
  get firstName() {
    return this.signup.get('firstName');
  }

  get firstNameInvalid() {
    return (
      this.firstName?.invalid &&
      (this.firstName?.dirty || this.firstName?.touched)
    );
  }

  get lastName() {
    return this.signup.get('lastName');
  }

  get lastNameInvalid() {
    return (
      this.lastName?.invalid && (this.lastName?.dirty || this.lastName?.touched)
    );
  }

  get dateOfBirth() {
    return this.signup.get('dateOfBirth');
  }

  get dateOfBirthInvalid() {
    return (
      this.dateOfBirth?.invalid &&
      (this.dateOfBirth?.dirty || this.dateOfBirth?.touched)
    );
  }

  get email() {
    return this.signup.get('email');
  }

  get emailInvalid() {
    return this.email?.invalid && (this.email?.dirty || this.email?.touched);
  }

  get password() {
    return this.signup.get('password');
  }

  get passwordInvalid() {
    return (
      this.password?.invalid && (this.password?.dirty || this.password?.touched)
    );
  }

  signupHandler() {
    if (this.signup.invalid) {
      this.signup.markAllAsTouched();
      return;
    }

    const signupInfo: SignupInfo = {
      firstName: this.firstName?.value,
      lastName: this.lastName?.value,
      dateOfBirth: this.dateOfBirth?.value,
      email: this.email?.value,
      password: this.password?.value,
    };

    this.signupService.register(
      signupInfo,
      () => {
        this.loginService.authenticate(
          { email: this.email?.value, password: this.password?.value },
          () => this.router.navigateByUrl('/home'), // Todo: replace with a home service
          (errMesg) => (this.signupStatus = errMesg as string)
        );
      },
      (errMesg) => (this.signupStatus = errMesg as string)
    );
  }
}
