import { Component, Input } from '@angular/core';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { CalendarEventComponent } from '@shared/calendar-event/calendar-event.component';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [CalendarEventComponent, AsyncPipe],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  @Input({ required: true }) weekDays$!: Observable<CalendarWeekDay[]>;
  @Input({ required: true }) calendarEvents$!: Observable<CalendarEvent[]>;

  public readonly hours: number[] = [...Array(24).keys()];
  public readonly hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );

  scheduledEvents(
    hour: number,
    dayOfMonth: number
  ): Observable<CalendarEvent[]> {
    return this.calendarEvents$.pipe(
      map((calendarEvents) => {
        if (!calendarEvents) {
          return [];
        }

        return calendarEvents
          .filter(
            (event) =>
              event.start.getDate() === dayOfMonth &&
              event.start.getHours() === hour
          )
          .sort((a, b) => a.start.valueOf() - b.start.valueOf());
      })
    );
  }

  clicked(hour: string, weekDay: { dayOfWeek: string; dayOfMonth: number }) {
    console.log(hour, weekDay);
  }

  onEventClicked(calendarEvent: CalendarEvent) {
    console.log(calendarEvent);
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
