import {
  HttpClient,
  HttpContext,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Injectable, OnInit, inject } from '@angular/core';
import { LoginInfo, SignupInfo, JwtToken, JwtTokenPack } from '@shared/types';
import { JwtKeeperService } from './jwt-keeper.service';
import { BYPASS_AUTH_HEADER } from '@interceptors/auth-header.interceptor';
import { ApiRouteService } from './api-route.service';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';

// todo: rename to AuthService
@Injectable({
  providedIn: 'root',
})
export class AuthService implements OnInit {
  private _authenticated$ = new BehaviorSubject<boolean>(false);
  private _http = inject(HttpClient);
  private _jwtKeeperService = inject(JwtKeeperService);
  private _apiRouteService = inject(ApiRouteService);

  ngOnInit(): void {
    // this._authenticated$
  }

  get authenticated$(): Observable<boolean> {
    return this._authenticated$.asObservable();
  }

  get authenticated(): boolean {
    return this._authenticated$.value;
  }

  get accessToken() {
    return this._jwtKeeperService.accessToken;
  }

  get refreshToken() {
    return this._jwtKeeperService.refreshToken;
  }

  get tokensValid(): boolean {
    return this._jwtKeeperService.tokensValid;
  }

  authenticate(loginInfo: LoginInfo) {
    return this._http
      .post<HttpResponse<JwtTokenPack | null>>(
        this._apiRouteService.route('/auth/login'),
        loginInfo,
        {
          responseType: 'json',
          context: new HttpContext().set(BYPASS_AUTH_HEADER, true),
        }
      )
      .pipe(
        tap((response) => {
          if (response.ok) {
            this._authenticated$.next(true);
            this._jwtKeeperService.save(response.body as JwtTokenPack);
          }
        })
      );
  }

  register(signupInfo: SignupInfo) {
    return this._http.post(
      this._apiRouteService.route('/auth/signup'),
      signupInfo,
      {
        responseType: 'text',
        context: new HttpContext().set(BYPASS_AUTH_HEADER, true),
      }
    );
  }

  refresh() {
    const refreshToken: JwtToken | null = this._jwtKeeperService.refreshToken;

    if (!refreshToken) {
      return;
    }

    return this._http.post(
      this._apiRouteService.route('/auth/refresh'),
      { refreshToken: refreshToken.token },
      {
        responseType: 'text', // todo: json
        context: new HttpContext().set(BYPASS_AUTH_HEADER, true), // todo:
      }
    );
  }
}
