import { Component } from '@angular/core';
import { CalendarWeekViewComponent } from './week-view/week-view.component';

@Component({
  selector: 'app-home-calendar',
  standalone: true,
  imports: [CalendarWeekViewComponent],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent {}
