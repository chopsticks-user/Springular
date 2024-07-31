import {Component, inject} from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {LoginComponent} from '../auth/login/login.component';
import {SignupComponent} from '../auth/signup/signup.component';
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    LoginComponent,
    SignupComponent,
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
