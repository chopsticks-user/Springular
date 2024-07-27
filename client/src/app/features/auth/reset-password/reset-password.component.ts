import {Component, inject} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from "@shared/services/auth.service";
import {Router} from "@angular/router";
import {FormControlErrorDictionary, LoginInfo} from "@shared/domain/types";
import {HttpErrorResponse} from "@angular/common/http";
import {FieldComponent} from "@core/layouts/form/field/field.component";
import {GroupComponent} from "@core/layouts/form/group/group.component";

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [
    MatIcon,
    ReactiveFormsModule,
    FieldComponent,
    GroupComponent
  ],
  templateUrl: './reset-password.component.html',
  styleUrl: '../../../shared/ui/auth-form.css'
})
export class ResetPasswordComponent {
  private _authService = inject(AuthService);
  private _router = inject(Router);

  public hidePassword = true;
  public status: string = '';
  public formGroup: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.email, Validators.required]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
    ]),
  });
  public readonly errorDictionaries: FormControlErrorDictionary[] = [
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

  public navigateToSignUp(): void {
    void this._router.navigateByUrl('/auth/signup');
  }

  public navigateToResetPassword(): void {
    void this._router.navigateByUrl('/auth/reset-password');
  }

  public resetPasswordHandler() {
    const loginInfo: LoginInfo = {
      email: this.formGroup.get('email')?.value,
      password: this.formGroup.get('password')?.value,
    };

    this._authService.authenticate(loginInfo).subscribe({
      next: () => this._router.navigateByUrl('/home'),
      error: (res: HttpErrorResponse) =>
        (this.status = res.error.description),
    });
  }
}
