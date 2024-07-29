import {Component, inject, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {FormControlErrorDictionary, SignupInfo} from '@shared/domain/types';
import {AuthService} from '@shared/services/auth.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {FieldComponent} from "@core/layouts/form/field/field.component";
import {GroupComponent} from "@core/layouts/form/group/group.component";
import {beforeNowValidator} from "@shared/directives/validators/before-now.validator";

@Component({
  selector: 'app-auth-signup',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatButtonModule,
    FieldComponent,
    GroupComponent,
  ],
  providers: [],
  templateUrl: './signup.component.html',
  styleUrl: '../../../shared/ui/auth-form.css',
})
export class SignupComponent {
  private _authService = inject(AuthService);
  private _router = inject(Router);

  public hidePassword = true;
  public formGroup: FormGroup = new FormGroup({
    firstName: new FormControl<string>(
      '',
      [Validators.required],
    ),
    lastName: new FormControl<string>(
      '',
      [Validators.required],
    ),
    dateOfBirth: new FormControl<Date>(
      new Date(),
      [
        Validators.required,
        beforeNowValidator(),
      ],
    ), // TODO: custom validator
    email: new FormControl<string>('', [Validators.email, Validators.required]),
    password: new FormControl<string>(
      '',
      [
        Validators.required,
        Validators.minLength(3),
      ],
    ),
  });
  public readonly errorDictionaries: FormControlErrorDictionary[] = [
    {
      name: 'firstName',
      entries: [
        {type: 'required', message: 'First name is required'},
      ],
    },
    {
      name: 'lastName',
      entries: [
        {type: 'required', message: 'Last name is required'},
      ],
    },
    {
      name: 'dateOfBirth',
      entries: [
        {type: 'required', message: 'Date of birth is required'},
        {type: 'beforeNow', message: 'Invalid date of birth'},
      ],
    },
    {
      name: 'email',
      entries: [
        {type: 'required', message: 'Email is required'},
        {type: 'email', message: 'Invalid email address'},
      ],
    },
    {
      name: 'password',
      entries: [
        {type: 'required', message: 'Password is required'},
        {type: 'minlength', message: 'Password must be at least 3 characters'},
      ],
    },
  ];

  public navigateToLogin(): void {
    void this._router.navigateByUrl('/auth/login');
  }

  public navigateToResetPassword(): void {
    void this._router.navigateByUrl('/auth/reset-password');
  }

  public signupHandler =
    (errorMessageSignal: WritableSignal<string>): void => {
      if (this.formGroup.invalid) {
        this.formGroup.markAllAsTouched();
        return;
      }

      const signupInfo: SignupInfo = {
        firstName: this.formGroup.get('firstName')?.value,
        lastName: this.formGroup.get('lastName')?.value,
        dateOfBirth: this.formGroup.get('dateOfBirth')?.value,
        email: this.formGroup.get('email')?.value,
        password: this.formGroup.get('password')?.value,
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
              error: (res: HttpErrorResponse) =>
                errorMessageSignal.set(res.error.description)
            });
        },
        error: (res: HttpErrorResponse) =>
          errorMessageSignal.set(res.error.description),
      });
    }
}
