import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '@services/auth.service';
import { Location } from '@angular/common';

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
  ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css',
})
export class WelcomeComponent {
  @ViewChild('authModal', { static: true })
  private _authModal!: ElementRef<HTMLDialogElement>;
  private _location = inject(Location);
  private _authService = inject(AuthService);

  showModal: boolean = false;
  showLogin: boolean = false;

  constructor() {
    const verifyResponse$ = this._authService.verify();

    if (!verifyResponse$) {
      return;
    }

    verifyResponse$.subscribe(() => {
      this._location.historyGo(-1);
    });
  }

  openAuthModal() {
    this.showModal = true;
    this._authModal.nativeElement.showModal();
  }

  closeAuthModal() {
    this.showModal = false;
    this._authModal.nativeElement.close();
  }
}
