import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
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

  showLogin: boolean = false;

  openAuthModal() {
    this.authModal.nativeElement.showModal();
  }

  closeAuthModal() {
    // TODO: clear input before closing
    this.authModal.nativeElement.close();
  }
}
