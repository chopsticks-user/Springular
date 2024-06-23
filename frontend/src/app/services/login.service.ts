import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { LoginInfo } from '../models/loginInfo';
import { AuthResponse } from '../shared/types';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  authenticated: boolean = false;

  http = inject(HttpClient);

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
          this.authenticated = true;
          const tokens: AuthResponse = JSON.parse(res);
          console.log(tokens);
          localStorage.setItem(
            'accessToken',
            JSON.stringify(tokens.accessToken)
          );
          localStorage.setItem(
            'refreshToken',
            JSON.stringify(tokens.refreshToken)
          );
          return callback && callback(res);
        },
        error: (err: HttpErrorResponse) => {
          return (
            errorCallback && errorCallback(JSON.parse(err.error).description)
          );
        },
      });
  }
}
