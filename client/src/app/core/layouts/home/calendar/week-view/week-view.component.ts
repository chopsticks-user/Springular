import {Component, inject, input, output} from '@angular/core';
import {CalendarEvent, CalendarWeekDay} from '@shared/domain/types';
import {EventComponent} from '@core/layouts/home/calendar/event/event.component';
import {Observable} from 'rxjs';
import {AsyncPipe} from '@angular/common';
import {DateTimeService} from '@features/home/calendar/date-time.service';

@Component({
  selector: 'app-layout-home-calendar-week-view',
  standalone: true,
  imports: [EventComponent, AsyncPipe],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  private _dateTimeService = inject(DateTimeService);

  public calendarEvents = input.required<CalendarEvent[]>();
  public onEventClicked = output<CalendarEvent>();
  public readonly hours: number[] = [...Array(24).keys()];
  public readonly hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );
  public readonly pixelsPerHour = 100;

  public get weekDays$(): Observable<CalendarWeekDay[]> {
    return this._dateTimeService.currentWeekDays$;
  }

  public scheduledEvents(hour: number, dayOfMonth: number): CalendarEvent[] {
    return this.calendarEvents().filter(
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
