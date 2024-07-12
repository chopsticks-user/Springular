import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { DateTime, Info } from 'luxon';
import {
  BehaviorSubject,
  map,
  Observable,
  shareReplay,
  switchMap,
  tap,
} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CalendarEventsService {
  private $calendarEvents = new BehaviorSubject<CalendarEvent[]>([]);
  private $today = new BehaviorSubject<DateTime>(DateTime.local());
  private $currentTime = new BehaviorSubject<DateTime>(DateTime.local());
  private _http = inject(HttpClient);

  public firstDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => {
      return currentTime.startOf('week').toLocal();
    })
  );

  public lastDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => currentTime.endOf('week').toLocal())
  );

  public currentWeekDays$: Observable<CalendarWeekDay[]> =
    this.firstDayOfWeek$.pipe(
      map((firstDayOfWeek) =>
        Info.weekdays('short').map((weekDayName, index) => ({
          dayOfWeek: weekDayName,
          dayOfMonth: firstDayOfWeek.plus({ days: index }).day,
        }))
      )
    );

  public get calendarEvents$(): Observable<CalendarEvent[]> {
    return this.$calendarEvents;
  }

  public get today$(): Observable<DateTime> {
    return this.$today;
  }

  public get currentTime$(): Observable<DateTime> {
    return this.$currentTime;
  }

  public updateToday(): void {
    this.$today.next(DateTime.local());
  }

  public resetCurrentTime(): void {
    this.$currentTime.next(DateTime.local());
  }

  public currentTimeToNextWeek() {
    this.$currentTime.next(this.$currentTime.value.plus({ days: 7 }));
  }

  public currentTimeToLastWeek() {
    this.$currentTime.next(this.$currentTime.value.minus({ days: 7 }));
  }

  public load(interval: string): Observable<CalendarEvent[]> {
    this.firstDayOfWeek$
      .pipe(
        tap((start) => {
          this._http
            .get<CalendarEvent[]>('/events', {
              params: {
                interval: interval,
                start: start.toJSDate().toISOString(),
              },
              // observe: 'response',
            })
            .subscribe((calendarEvents) => {
              this.$calendarEvents.next(calendarEvents);
            });
        })
      )
      .subscribe();
    return this.calendarEvents$;
  }

  public addCalendarEvent(event: CalendarEvent): Observable<CalendarEvent> {
    return this._http
      .post<CalendarEvent>('/events', event, {
        // observe: 'response',
      })
      .pipe(
        tap((newCalendarEvent) => {
          console.log(newCalendarEvent);

          const newEvents = [...this.$calendarEvents.value];
          const insertIndex = newEvents.findIndex(
            (calendarEvent) =>
              new Date(calendarEvent.start) > new Date(newCalendarEvent.start)
          );

          if (insertIndex === -1) {
            newEvents.push(newCalendarEvent);
          } else {
            newEvents.splice(insertIndex, 0, newCalendarEvent);
          }

          console.log(newEvents);
          this.$calendarEvents.next(newEvents);
        })
      );
  }

  public editCalendarEvent(event: CalendarEvent): Observable<CalendarEvent> {
    return this._http
      .put<CalendarEvent>(`/events/${event.id}`, event, {
        // observe: 'response',
      })
      .pipe(
        tap((editedCalendarEvent) => {
          this.$calendarEvents.next(
            this.$calendarEvents.value.map((calendarEvent) =>
              calendarEvent.id === editedCalendarEvent.id
                ? editedCalendarEvent
                : calendarEvent
            )
          );
        })
      );
  }

  public deleteCalendarEvent(event: CalendarEvent): Observable<void> {
    return this._http
      .delete<void>(`/events/${event.id}`, {
        // observe: 'response',
        body: event,
      })
      .pipe(
        tap(() => {
          this.$calendarEvents.next(
            this.$calendarEvents.value.filter(
              (calendarEvent) => event.id !== calendarEvent.id
            )
          );
        })
      );
  }
}
