import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { LoginComponent } from '../login/login.component';
import { SignupComponent } from '../signup/signup.component';
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
  @ViewChild('authModal') authModal!: ElementRef<HTMLDialogElement>;

  showModal: boolean = false;
  showLogin: boolean = false;

  openAuthModal() {
    this.showModal = true;
    this.authModal.nativeElement.showModal();
  }

  closeAuthModal() {
    this.showModal = false;
    this.authModal.nativeElement.close();
  }
}
