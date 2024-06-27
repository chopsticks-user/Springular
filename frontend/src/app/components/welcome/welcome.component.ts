import {
  Component,
  ElementRef,
  OnInit,
  ViewChild,
  inject,
} from '@angular/core';
import {
  ActivatedRoute,
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { MatIconModule } from '@angular/material/icon';
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
export class WelcomeComponent implements OnInit {
  @ViewChild('authModal', { static: true })
  private _authModal!: ElementRef<HTMLDialogElement>;

  private _location = inject(Location);

  private _route = inject(ActivatedRoute);

  showModal: boolean = false;
  showLogin: boolean = false;

  // todo: ping endpoint
  constructor() {}

  ngOnInit(): void {
    this._route.data.subscribe((data) => {
      switch (data['action']) {
        case 'login': {
          this.showLogin = true;
          this.openAuthModal();
          return;
        }
        case 'signup': {
          this.showLogin = false;
          this.openAuthModal();
          return;
        }
        default:
          return;
      }
    });
  }

  openAuthModal() {
    this.showModal = true;
    this._authModal.nativeElement.showModal();
  }

  closeAuthModal() {
    this.showModal = false;
    this._location.go('/');
    this._authModal.nativeElement.close();
  }
}
