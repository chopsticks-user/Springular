import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { TokenPack, LoginInfo } from '../shared/types';
import { JwtKeeperService } from './jwt-keeper.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  authenticated: boolean = false;

  http = inject(HttpClient);

  jwtKeeper = inject(JwtKeeperService);

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
          this.jwtKeeper.save(res);
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
