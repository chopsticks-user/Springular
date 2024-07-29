import {Component, inject, OnInit} from '@angular/core';
import {CalendarComponent} from './calendar/calendar.component';
import {HeaderComponent} from '@core/layouts/home/header/header.component';
import {NavigationBarComponent} from '@core/layouts/home/navigation-bar/navigation-bar.component';
import {RouterOutlet} from '@angular/router';
import {UserService} from '@shared/services/user.service';
import {ConfirmationComponent} from "@core/layouts/dialog/confirmation/confirmation.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavigationBarComponent, HeaderComponent, CalendarComponent, RouterOutlet, NavigationBarComponent, ConfirmationComponent,],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  private _userService = inject(UserService);

  ngOnInit(): void {
    this._userService.load();
  }
}
