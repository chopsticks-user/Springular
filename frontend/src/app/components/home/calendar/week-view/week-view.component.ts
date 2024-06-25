import { Component } from '@angular/core';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  hours: string[] = [
    '12:00 AM',
    '1:00 AM',
    '2:00 AM',
    '3:00 AM',
    '4:00 AM',
    '5:00 AM',
    '6:00 AM',
    '7:00 AM',
    '8:00 AM',
    '9:00 AM',
    '10:00 AM',
    '11:00 AM',
    '12:00 PM',
    '1:00 PM',
    '2:00 PM',
    '3:00 PM',
    '4:00 PM',
    '5:00 PM',
    '6:00 PM',
    '7:00 PM',
    '8:00 PM',
    '9:00 PM',
    '10:00 PM',
    '11:00 PM',
  ];

  weekDays: { dayOfWeek: string; dayOfMonth: number }[] = [
    { dayOfWeek: 'Sunday', dayOfMonth: 15 },
    { dayOfWeek: 'Monday', dayOfMonth: 16 },
    { dayOfWeek: 'Tuesday', dayOfMonth: 17 },
    { dayOfWeek: 'Wednesday', dayOfMonth: 18 },
    { dayOfWeek: 'Thursday', dayOfMonth: 19 },
    { dayOfWeek: 'Friday', dayOfMonth: 20 },
    { dayOfWeek: 'Saturday', dayOfMonth: 21 },
  ];
}