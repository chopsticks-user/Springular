import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { CalendarEvent } from '@shared/types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CalendarEventsService {
  private _http = inject(HttpClient);

  public getCalendarEvents(
    interval: string,
    start: Date
  ): Observable<HttpResponse<CalendarEvent[]>> {
    return this._http.get<CalendarEvent[]>('/events', {
      params: { interval: interval, start: start.toISOString() },
      observe: 'response',
    });
  }

  public addCalendarEvent(
    event: CalendarEvent
  ): Observable<HttpResponse<CalendarEvent>> {
    return this._http.post<CalendarEvent>('/events', event, {
      observe: 'response',
    });
  }

  public editCalendarEvent(
    event: CalendarEvent
  ): Observable<HttpResponse<CalendarEvent>> {
    return this._http.put<CalendarEvent>('/events', event, {
      observe: 'response',
    });
  }

  public deleteCalendarEvent(
    event: CalendarEvent
  ): Observable<HttpResponse<CalendarEvent>> {
    return this._http.delete<CalendarEvent>('/events', {
      observe: 'response',
      body: event,
    });
  }
}
