import {Component, inject} from '@angular/core';
import {Router} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators,} from '@angular/forms';
import {AuthService} from '@shared/services/auth.service';
import {LoginInfo} from '@shared/domain/types';
import {HttpErrorResponse} from '@angular/common/http';
import {FieldComponent} from "@core/layouts/form/field/field.component";
import {FormGroupComponent} from "@core/layouts/form/group/form-group.component";

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
    FieldComponent,
    FormGroupComponent,
  ],
  templateUrl: './login.component.html',
  styleUrl: '../../../shared/ui/auth-form.css',
})
export class LoginComponent {
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

  public navigateToSignUp(): void {
    void this._router.navigateByUrl('/auth/signup');
  }

  public navigateToResetPassword(): void {
    void this._router.navigateByUrl('/auth/reset-password');
  }

  public loginHandler() {
    if (this.formGroup.invalid) {
      this.formGroup.markAllAsTouched();
      return;
    }

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
