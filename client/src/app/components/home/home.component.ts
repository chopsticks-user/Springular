import {Component, inject, OnInit} from '@angular/core';
import {CalendarComponent} from './calendar/calendar.component';
import {HeaderComponent} from './header/header.component';
import {SidebarComponent} from './sidebar/sidebar.component';
import {RouterOutlet} from '@angular/router';
import {UserService} from '@services/user.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [SidebarComponent, HeaderComponent, CalendarComponent, RouterOutlet],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  private _userService = inject(UserService);

  ngOnInit(): void {
    this._userService.load();
  }
}
