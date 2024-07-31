import {Component, inject} from '@angular/core';
import {NotificationService} from "@shared/services/notification.service";
import {AsyncPipe} from "@angular/common";
import {NotificationLevel} from "@shared/domain/types";
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-dialog-notification',
  standalone: true,
  imports: [AsyncPipe, IconComponent,],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css'
})
export class NotificationComponent {
  private _notificationService = inject(NotificationService);

  public displayed$ = this._notificationService.displayed$;
  public message$ = this._notificationService.message$;
  public level$ = this._notificationService.level$;

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
