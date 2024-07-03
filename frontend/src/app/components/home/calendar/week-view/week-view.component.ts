import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { CalendarEventComponent } from '@shared/calendar-event/calendar-event.component';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [CalendarEventComponent, AsyncPipe, MatIcon],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  @Input({ required: true }) public weekDays$!: Observable<CalendarWeekDay[]>;
  @Input({ required: true }) public calendarEvents$!: Observable<
    CalendarEvent[]
  >;
  @Output() public $eventClicked = new EventEmitter<CalendarEvent>();

  public readonly hours: number[] = [...Array(24).keys()];
  public readonly hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );

  public scheduledEvents(
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

  public onEventClicked(calendarEvent: CalendarEvent) {
    this.$eventClicked.emit(calendarEvent);
  }

  public hourTotext(hour: number): string {
    switch (hour) {
      case 0:
        return '12:00 AM';
      case 12:
        return '12:00 PM';
      default:
        return hour < 12 ? `${hour}:00 AM` : `${hour - 12}:00 PM`;
    }
  }
}
