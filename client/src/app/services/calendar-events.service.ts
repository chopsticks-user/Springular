import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {CalendarEvent} from '@shared/types';
import {DateTime} from 'luxon';
import {BehaviorSubject, Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CalendarEventsService {
  private _http = inject(HttpClient);
  private $calendarEvents = new BehaviorSubject<CalendarEvent[]>([]);

  public get calendarEvents$(): Observable<CalendarEvent[]> {
    return this.$calendarEvents;
  }

  public load(interval: string, start: DateTime): Observable<CalendarEvent[]> {
    // this.firstDayOfWeek$
    //   .pipe(
    //     tap((start) => {
    //       this._http
    //         .get<CalendarEvent[]>('/events', {
    //           params: {
    //             interval: interval,
    //             start: start.toJSDate().toISOString(),
    //           },
    //           // observe: 'response',
    //         })
    //         .subscribe((calendarEvents) => {
    //           this.$calendarEvents.next(calendarEvents);
    //         });
    //     })
    //   )
    //   .subscribe();
    // return this.calendarEvents$;

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
    return this.calendarEvents$;
  }

  public addCalendarEvent(event: CalendarEvent): Observable<CalendarEvent> {
    return this._http
      .post<CalendarEvent>('/events', event, {
        // observe: 'response',
      })
      .pipe(
        tap((newCalendarEvent) => {
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
