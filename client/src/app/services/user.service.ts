import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {UserInfo} from '@shared/types';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private _http = inject(HttpClient);
  private $userInfo = new BehaviorSubject<UserInfo>({
    id: 'unknown',
    firstName: 'John',
    lastName: 'Doe',
    dateOfBirth: '07-11-1988',
    email: 'johndoe@email.springular.com',
  });

  public get userInfo(): Observable<UserInfo> {
    return this.$userInfo;
  }

  public load(): void {
    this._http.get<UserInfo>('/users/me').subscribe((res) => {
      this.$userInfo.next(res);
    });
  }
}
