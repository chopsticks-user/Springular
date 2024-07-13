import { Component, ElementRef, ViewChild } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { MatIconModule } from '@angular/material/icon';

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

  public showModal: boolean = false;
  public showLogin: boolean = false;

  openAuthModal() {
    this.showModal = true;
    this._authModal.nativeElement.showModal();
  }

  closeAuthModal() {
    this.showModal = false;
    this._authModal.nativeElement.close();
  }
}
