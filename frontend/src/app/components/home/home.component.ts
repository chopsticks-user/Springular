import { Component, inject } from '@angular/core';
import { AuthService } from '@services/auth.service';
import { CalendarComponent } from './calendar/calendar.component';
import { HeaderComponent } from './header/header.component';
import { SidebarComponent } from './sidebar/sidebar.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent, CalendarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {
  private _authService = inject(AuthService);
}
