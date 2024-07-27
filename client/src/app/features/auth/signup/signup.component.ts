import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {SignupInfo} from '@shared/domain/types';
import {AuthService} from '@shared/services/auth.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {FieldComponent} from "@core/layouts/form/field/field.component";

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatButtonModule,
    FieldComponent,
  ],
  providers: [],
  templateUrl: './signup.component.html',
  styleUrl: '../../../shared/ui/auth-form.css',
})
export class SignupComponent {
  private _authService = inject(AuthService);
  private _router = inject(Router);

  public hidePassword = true;
  public status: string = '';
  public formGroup: FormGroup = new FormGroup({
    firstName: new FormControl<string>('', [Validators.required]),
    lastName: new FormControl<string>('', [Validators.required]),
    dateOfBirth: new FormControl<Date>(new Date(), [Validators.required]), // TODO: custom validator
    email: new FormControl<string>('', [Validators.email, Validators.required]),
    password: new FormControl<string>('', [
      Validators.required,
      Validators.minLength(3),
    ]),
  });

  public navigateToLogin(): void {
    void this._router.navigateByUrl('/auth/login');
  }

  public navigateToResetPassword(): void {
    void this._router.navigateByUrl('/auth/reset-password');
  }

  public signupHandler() {
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
            error: (error: HttpErrorResponse) =>
              (this.status = JSON.parse(error.error)
                .description as string),
          });
      },
      error: (error: HttpErrorResponse) =>
        (this.status = JSON.parse(error.error).description as string),
    });
  }

  protected readonly FormControl = FormControl;
}
