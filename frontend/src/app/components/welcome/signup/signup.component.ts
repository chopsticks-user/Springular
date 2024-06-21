import { Component, inject } from '@angular/core';
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
  ],
  providers: [provideNativeDateAdapter()],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {
  signup: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    dateOfBirth: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
    ]),
  });

  signupService = inject(SignupService);

  router = inject(Router);

  hide = true;

  // TODO: verify input validity
  get firstName() {
    return this.signup.get('firstName')?.value;
  }

  get lastName() {
    return this.signup.get('lastName')?.value;
  }

  get dateOfBirth() {
    return this.signup.get('dateOfBirth')?.value;
  }

  get email() {
    return this.signup.get('email')?.value;
  }

  get password() {
    return this.signup.get('password')?.value;
  }

  signupHandler() {
    const signupInfo: SignupInfo = {
      firstName: this.firstName,
      lastName: this.lastName,
      dateOfBirth: this.dateOfBirth,
      email: this.email,
      password: this.password,
    };
    this.signupService.register(signupInfo, () => {
      this.router.navigateByUrl('/home');
    });
  }
}
