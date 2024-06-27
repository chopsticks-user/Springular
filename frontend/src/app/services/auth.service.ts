import {
  HttpClient,
  HttpContext,
  HttpErrorResponse,
} from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { LoginInfo, SignupInfo, JwtToken } from '@shared/types';
import { JwtKeeperService } from './jwt-keeper.service';
import { BYPASS_AUTH_HEADER } from '@interceptors/auth-header.interceptor';
import { ApiRouteService } from './api-route.service';
import { map, tap } from 'rxjs';

// todo: rename to AuthService
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _authenticated: boolean = false;

  private _http = inject(HttpClient);

  private _jwtKeeperService = inject(JwtKeeperService);

  private _apiRouteService = inject(ApiRouteService);

  get authenticated(): boolean {
    return this._authenticated;
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
      .post(this._apiRouteService.route('/auth/login'), loginInfo, {
        responseType: 'text',
        context: new HttpContext().set(BYPASS_AUTH_HEADER, true),
      })
      .pipe(
        tap((response) => {
          this._authenticated = true;
          this._jwtKeeperService.save(response);
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

  refresh(
    onSuccess?: (res?: string) => void,
    onFailure?: (errMesg?: string) => void
  ): void {
    const refreshToken: JwtToken | null = this._jwtKeeperService.refreshToken;

    if (!refreshToken) {
      return;
    }

    this._http
      .post(
        this._apiRouteService.route('/auth/refresh'),
        { refreshToken: refreshToken.token },
        {
          responseType: 'text', // todo: json
          context: new HttpContext().set(BYPASS_AUTH_HEADER, true), // todo:
        }
      )
      .subscribe({
        next: (res) => {
          this._authenticated = true;
          this._jwtKeeperService.save(res);
          return onSuccess && onSuccess(res);
        },
        error: (err: HttpErrorResponse) => {
          this._authenticated = false;
          this._jwtKeeperService.clear();
          return onFailure && onFailure(JSON.parse(err.error).description);
        },
      });
  }
}
