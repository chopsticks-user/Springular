import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { UserService } from '@services/user.service';
import { UserInfo } from '@shared/types';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home-sidebar-user-section',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './user-section.component.html',
  styleUrl: './user-section.component.css',
})
export class UserSectionComponent {
  private _userService = inject(UserService);

  public userInfo$: Observable<UserInfo> = this._userService.userInfo;
}
