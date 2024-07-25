import {HttpClient, HttpContext, HttpResponse} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {JwtToken, JwtTokenPack, LoginInfo, SignupInfo} from '@shared/domain/types';
import {JwtKeeperService} from './jwt-keeper.service';
import {BYPASS_AUTH_HEADER} from '@shared/domain/constants';
import {BehaviorSubject, finalize, Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private $authenticated = new BehaviorSubject<boolean>(false);
  private _http = inject(HttpClient);
  private _jwtKeeperService = inject(JwtKeeperService);

  get authenticated$(): Observable<boolean> {
    return this.$authenticated;
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

  authenticate(loginInfo: LoginInfo): Observable<HttpResponse<JwtTokenPack>> {
    return this._http
      .post<JwtTokenPack>('/auth/login', loginInfo, {
        observe: 'response',
        context: new HttpContext().set(BYPASS_AUTH_HEADER, true),
      })
      .pipe(
        tap((response: HttpResponse<JwtTokenPack>) => {
          if (response.ok) {
            this.$authenticated.next(true);
            this._jwtKeeperService.save(response.body as JwtTokenPack);
          }
        })
      );
  }

  verify(): Observable<HttpResponse<void>> | null {
    const refreshToken: JwtToken | null = this.refreshToken;

    if (!refreshToken) {
      return null;
    }

    // todo: an interceptor to refresh access token
    return this._http
      .post<void>(
        '/verify',
        { refreshToken: refreshToken.token },
        { observe: 'response' }
      )
      .pipe(
        tap((response) => {
          if (response.ok) {
            this.$authenticated.next(true);
          }
        })
      );
  }

  register(signupInfo: SignupInfo): Observable<HttpResponse<SignupInfo>> {
    // todo: backend should return nothing
    return this._http.post<SignupInfo>('/auth/signup', signupInfo, {
      observe: 'response',
      context: new HttpContext().set(BYPASS_AUTH_HEADER, true),
    });
  }

  refresh(): Observable<HttpResponse<JwtTokenPack>> | null {
    const refreshToken: JwtToken | null = this._jwtKeeperService.refreshToken;

    if (!refreshToken) {
      return null; // todo: redirect to login
    }

    return this._http
      .post<JwtTokenPack>(
        '/auth/refresh',
        { refreshToken: refreshToken.token },
        {
          observe: 'response',
          context: new HttpContext().set(BYPASS_AUTH_HEADER, true),
        }
      )
      .pipe(
        tap((response: HttpResponse<JwtTokenPack>) => {
          if (response.ok) {
            this.$authenticated.next(true);
            this._jwtKeeperService.save(response.body as JwtTokenPack);
          }
        })
      );
  }

  logout(): void {
    this._http
      .get<void>('/logout', {
        observe: 'response',
      })
      .pipe(
        finalize(() => {
          this.$authenticated.next(false);
          this._jwtKeeperService.clear();
        })
      )
      .subscribe();
  }
}
