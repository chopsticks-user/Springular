import { Component, inject, Input, output } from '@angular/core';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { CalendarEventComponent } from '@shared/calendar-event/calendar-event.component';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { DateTimeService } from '@services/date-time.service';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [CalendarEventComponent, AsyncPipe, MatIcon],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  private _dateTimeService = inject(DateTimeService);

  @Input({ required: true }) public calendarEvents!: CalendarEvent[];
  public onEventClicked = output<CalendarEvent>();
  public readonly hours: number[] = [...Array(24).keys()];
  public readonly hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );

  public get weekDays$(): Observable<CalendarWeekDay[]> {
    return this._dateTimeService.currentWeekDays$;
  }

  public scheduledEvents(hour: number, dayOfMonth: number): CalendarEvent[] {
    return this.calendarEvents.filter(
      (event) =>
        event != null &&
        new Date(event.start).getHours() === hour &&
        new Date(event.start).getDate() === dayOfMonth
    );
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
