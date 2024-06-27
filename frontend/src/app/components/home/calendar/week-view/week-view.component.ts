import { Component } from '@angular/core';
import { CalendarEvent } from '@shared/types';
import { CalendarEventComponent } from '@shared/calendar-event/calendar-event.component';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [CalendarEventComponent],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  hours: number[] = [...Array(24).keys()];
  hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );

  weekDays: { dayOfWeek: string; dayOfMonth: number }[] = [
    { dayOfWeek: 'Sunday', dayOfMonth: 15 },
    { dayOfWeek: 'Monday', dayOfMonth: 16 },
    { dayOfWeek: 'Tuesday', dayOfMonth: 17 },
    { dayOfWeek: 'Wednesday', dayOfMonth: 18 },
    { dayOfWeek: 'Thursday', dayOfMonth: 19 },
    { dayOfWeek: 'Friday', dayOfMonth: 20 },
    { dayOfWeek: 'Saturday', dayOfMonth: 21 },
  ];

  // todo: events should be sorted
  events: CalendarEvent[] = [
    {
      id: 0,
      title: 'Event',
      description: '',
      start: new Date(2024, 6, 15, 6, 30),
      durationMinutes: 30,
      repeat: 'none',
      color: 'green',
    },
    {
      id: 1,
      title: 'Event',
      description: '',
      start: new Date(2024, 6, 18, 15, 45),
      durationMinutes: 60,
      repeat: 'none',
      color: 'yellow',
    },
    {
      id: 2,
      title: 'Event',
      description: '',
      start: new Date(2024, 6, 18, 15, 15),
      durationMinutes: 15,
      repeat: 'none',
      color: 'red',
    },
    {
      id: 3,
      title: 'Event',
      description: '',
      start: new Date(2024, 6, 18, 15),
      durationMinutes: 5,
      repeat: 'none',
      color: 'blue',
    },
    {
      id: 4,
      title: 'Event',
      description: '',
      start: new Date(2024, 6, 18, 15, 5),
      durationMinutes: 10,
      repeat: 'none',
      color: 'orange',
    },
  ];

  scheduledEvents(hour: number, dayOfMonth: number): CalendarEvent[] {
    return this.events
      .filter(
        (event) =>
          event.start.getDate() === dayOfMonth &&
          event.start.getHours() === hour
      )
      .sort((a, b) => a.start.valueOf() - b.start.valueOf());
  }

  clicked(hour: string, weekDay: { dayOfWeek: string; dayOfMonth: number }) {
    console.log(hour, weekDay);
  }

  onClick(event: CalendarEvent) {
    console.log(event);
  }

  hourTotext(hour: number): string {
    if (hour === 0) {
      return '12:00 AM';
    }

    if (hour === 12) {
      return '12:00 PM';
    }

    return hour < 12 ? `${hour}:00 AM` : `${hour - 12}:00 PM`;
  }
}
