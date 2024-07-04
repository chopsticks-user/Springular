import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { JwtKeeperService } from './jwt-keeper.service';
import { ApiRouteService } from './api-route.service';
import { CalendarEvent } from '@shared/types';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CalendarServiceService {
  private _http = inject(HttpClient);

  public getCalendarEvents(
    from: Date,
    to: Date
  ): Observable<HttpResponse<CalendarEvent[]>> {
    return this._http.get<CalendarEvent[]>('/calendars', {
      params: { from: from.toISOString(), to: to.toISOString() },
      observe: 'response',
    });
  }

  public addCalendarEvent(
    event: CalendarEvent
  ): Observable<HttpResponse<CalendarEvent>> {
    return this._http.post<CalendarEvent>('/calendars', event, {
      observe: 'response',
    });
  }

  public editCalendarEvent(
    event: CalendarEvent
  ): Observable<HttpResponse<CalendarEvent>> {
    return this._http.put<CalendarEvent>('/calendars', event, {
      observe: 'response',
    });
  }

  public deleteCalendarEvent(
    event: CalendarEvent
  ): Observable<HttpResponse<CalendarEvent>> {
    return this._http.delete<CalendarEvent>('/calendars', {
      observe: 'response',
      body: event,
    });
  }
}
