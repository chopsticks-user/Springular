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
    this.http
      .post('http://localhost:8080/api/login', loginInfo, {
        responseType: 'text',
      })
      .subscribe((res) => {
        // TODO:
        console.log(res);
        this.authenticated = true;
        return callback && callback();
      });
  }
}
