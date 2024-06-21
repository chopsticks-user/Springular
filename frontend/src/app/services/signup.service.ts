import { Injectable, inject } from '@angular/core';
import { SignupInfo } from '../models/signupInfo';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  http = inject(HttpClient);

  register(signupInfo: SignupInfo, callback: any) {
    this.http
      .post('http://localhost:8080/api/auth/signup', signupInfo, {
        responseType: 'text',
      })
      .subscribe((res) => {
        console.log(res);
        return callback && callback();
      });
  }
}
