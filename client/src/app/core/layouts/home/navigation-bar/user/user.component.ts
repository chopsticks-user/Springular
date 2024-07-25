import {AsyncPipe} from '@angular/common';
import {Component, inject} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '@shared/services/user.service';
import {UserInfo} from '@shared/domain/types';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-home-navigation-bar-user',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './user.component.html',
  styleUrl: './user.component.css',
})
export class UserComponent {
  private _router = inject(Router);
  private _userService = inject(UserService);

  public userInfo$: Observable<UserInfo> = this._userService.userInfo;

  public goToProfilePage(): void {
    this._router.navigateByUrl('/home/profile');
  }
}
