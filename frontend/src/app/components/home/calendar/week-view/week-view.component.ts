import { Component, Input } from '@angular/core';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { CalendarEventComponent } from '@shared/calendar-event/calendar-event.component';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [CalendarEventComponent],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  @Input({ required: true }) weekDays!: CalendarWeekDay[] | null;
  @Input({ required: true }) calendarEvents!: CalendarEvent[] | null;

  public hours: number[] = [...Array(24).keys()];
  public hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );

  // weekDays: { dayOfWeek: string; dayOfMonth: number }[] = [
  //   { dayOfWeek: 'Sunday', dayOfMonth: 15 },
  //   { dayOfWeek: 'Monday', dayOfMonth: 16 },
  //   { dayOfWeek: 'Tuesday', dayOfMonth: 17 },
  //   { dayOfWeek: 'Wednesday', dayOfMonth: 18 },
  //   { dayOfWeek: 'Thursday', dayOfMonth: 19 },
  //   { dayOfWeek: 'Friday', dayOfMonth: 20 },
  //   { dayOfWeek: 'Saturday', dayOfMonth: 21 },
  // ];

  scheduledEvents(hour: number, dayOfMonth: number): CalendarEvent[] {
    if (!this.calendarEvents) {
      return [];
    }

    return this.calendarEvents
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
