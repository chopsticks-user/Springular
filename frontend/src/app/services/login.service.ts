import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { LoginInfo } from '../models/login-info';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  authenticated: boolean = false;

  http = inject(HttpClient);

  authenticate(loginInfo: LoginInfo, callback: any) {
    const headers = new HttpHeaders(
      loginInfo
        ? {
            authorization: `Basic ${btoa(
              loginInfo.email + ':' + loginInfo.password
            )}`,
          }
        : {}
    );

    this.http.get('/users', { headers: headers }).subscribe((res) => {
      // TODO:
      this.authenticated = true;
      return callback && callback();
    });
  }
}
