import {Component} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {AuthSectionComponent} from "@core/layouts/auth/section/auth-section.component";

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    RouterOutlet,
    AuthSectionComponent
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {

}
