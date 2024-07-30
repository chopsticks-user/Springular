import {Component, inject} from '@angular/core';
import {NotificationService} from "@shared/services/notification.service";
import {AsyncPipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {NotificationLevel} from "@shared/domain/types";

@Component({
  selector: 'app-layout-dialog-notification',
  standalone: true,
  imports: [
    AsyncPipe,
    MatIcon
  ],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  private _notificationService = inject(NotificationService);
  // private _stopAnimation = signal(false);

  public displayed$ = this._notificationService.displayed$;
  public message$ = this._notificationService.message$;
  public level$ = this._notificationService.level$;
  // public stopAnimation = this._stopAnimation.asReadonly();

  // public keepNotificationDialog() {
  //   this._stopAnimation.set(true);
  //   this._notificationService.clearTimer();
  // }
  //
  // public resetNotificationDialogTimer() {
  //   this._stopAnimation.set(false);
  //   this._notificationService.resetTimer();
  // }

  public close() {
    this._notificationService.close();
  }

  public getColor(level: NotificationLevel): string {
    switch (level) {
      case 'info':
        return 'var(--typography-primary)';
      case 'warning':
        return 'var(--static-yellow-accent)';
      case 'error':
        return 'var(--static-red-accent)';
    }
  }

  public getIcon(level: NotificationLevel): string {
    switch (level) {
      case 'info':
        return 'notifications_active';
      case 'warning':
        return 'warning';
      case 'error':
        return 'error';
    }
  }
}
