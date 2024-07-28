import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';
import {LoginComponent} from '../auth/login/login.component';
import {SignupComponent} from '../auth/signup/signup.component';
import {MatIconModule} from '@angular/material/icon';
import {MatSuffix} from "@angular/material/form-field";
import {ModalComponent} from "@core/layouts/modal/modal.component";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatButtonModule,
    LoginComponent,
    SignupComponent,
    MatIconModule,
    MatSuffix,
    ModalComponent,
    AsyncPipe,
  ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css',
})
export class WelcomeComponent {
  private _router = inject(Router);

  public modalShouldOpen = false;

  public getStarted(): void {
    void this._router.navigateByUrl('/auth/login');
  }
}
