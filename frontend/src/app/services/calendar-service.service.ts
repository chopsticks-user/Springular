import { HttpClient } from '@angular/common/http';
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
  private _jwtKeeperService = inject(JwtKeeperService);
  private _apiRouteService = inject(ApiRouteService);

  // public addCalendarEvent(event: CalendarEvent):Observable<CalendarEvent> {
  //   return this._http.post(this._apiRouteService)
  // }
}
