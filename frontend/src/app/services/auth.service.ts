import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { LoginInfo, Token } from '@shared/types';
import { JwtKeeperService } from './jwt-keeper.service';

// todo: rename to AuthService
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _authenticated: boolean = false;

  private http = inject(HttpClient);

  private jwtKeeperService = inject(JwtKeeperService);

  get authenticated(): boolean {
    return this._authenticated;
  }

  authenticate(
    loginInfo: LoginInfo,
    callback?: (res?: string) => void,
    errorCallback?: (errMesg?: string) => void
  ) {
    this.http
      .post('http://localhost:8080/api/auth/login', loginInfo, {
        responseType: 'text',
      })
      .subscribe({
        next: (res) => {
          this._authenticated = true;
          this.jwtKeeperService.save(res);
          return callback && callback(res);
        },
        error: (err: HttpErrorResponse) => {
          return (
            errorCallback && errorCallback(JSON.parse(err.error).description)
          );
        },
      });
  }

  refresh(): void {
    const refreshToken: Token | null = this.jwtKeeperService.refreshToken;

    if (!refreshToken) {
      return;
    }

    this.http
      .post(
        'http://localhost:8080/api/auth/refresh',
        { refreshToken: refreshToken.token },
        {
          responseType: 'text',
        }
      )
      .subscribe({
        next: (res) => {
          this._authenticated = true;
          this.jwtKeeperService.save(res);
        },
        error: () => {
          this._authenticated = false;
          this.jwtKeeperService.clear();
        },
      });
  }
}
