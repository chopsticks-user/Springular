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
  private _router = inject(Router);

  showModal: boolean = false;
  showLogin: boolean = false;

  constructor() {
    const verifyResponse$ = inject(AuthService).verify();

    if (!verifyResponse$) {
      return;
    }

    verifyResponse$.subscribe(() => {
      // todo: navigate to the prev route if exists
      void this._router.navigate(['/home']);
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
