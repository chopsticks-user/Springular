import { Injectable, inject } from '@angular/core';
import { SignupInfo } from '../models/signupInfo';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  http = inject(HttpClient);

  register(
    signupInfo: SignupInfo,
    callback?: (res?: string) => void,
    errorCallback?: (errMesg?: string) => void
  ) {
    this.http
      .post('http://localhost:8080/api/auth/signup', signupInfo, {
        responseType: 'text',
      })
      .subscribe({
        next: (res) => {
          return callback && callback(res);
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
          errorCallback && errorCallback(JSON.parse(err.error).description);
        },
      });
  }
}
