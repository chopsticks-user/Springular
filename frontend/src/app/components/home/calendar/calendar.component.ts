import { Component, ViewEncapsulation } from '@angular/core';
import { CalendarWeekViewComponent } from './week-view/week-view.component';
import { DateTime, Info } from 'luxon';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-home-calendar',
  standalone: true,
  imports: [CalendarWeekViewComponent, AsyncPipe, MatIcon],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent {
  private $today = new BehaviorSubject<DateTime>(DateTime.local());
  private $currentTime = new BehaviorSubject<DateTime>(DateTime.local());

  public today$: Observable<DateTime> = this.$today.asObservable();
  public currentFirstDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => currentTime.startOf('week').toLocal())
  );
  public currentLastDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => currentTime.endOf('week').toLocal())
  );
  public currentWeekDays$: Observable<CalendarWeekDay[]> =
    this.currentFirstDayOfWeek$.pipe(
      map((currentFirstDayOfWeek) =>
        Info.weekdays('short').map((weekDayName, index) => ({
          dayOfWeek: weekDayName,
          dayOfMonth: currentFirstDayOfWeek.plus({ days: index }).day,
        }))
      )
    );
  public calendarEvents$ = new BehaviorSubject<CalendarEvent[]>(
    // todo: events should be sorted
    [
      {
        id: '0',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 28, 6, 30),
        durationMinutes: 30,
        repeat: 'none',
        color: 'green',
      },
      {
        id: '1',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 27, 15, 45),
        durationMinutes: 60,
        repeat: 'none',
        color: 'yellow',
      },
      {
        id: '2',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 28, 15, 15),
        durationMinutes: 15,
        repeat: 'none',
        color: 'red',
      },
      {
        id: '3',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 28, 15),
        durationMinutes: 5,
        repeat: 'none',
        color: 'blue',
      },
      {
        id: '4',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 27, 15, 5),
        durationMinutes: 10,
        repeat: 'none',
        color: 'orange',
      },
    ]
  );

  get todayFormat() {
    return DateTime.DATETIME_MED;
  }

  nextHandler() {
    this.$currentTime.next(this.$currentTime.value.plus({ days: 7 }));
  }

  prevHandler() {
    this.$currentTime.next(this.$currentTime.value.minus({ days: 7 }));
  }
}
