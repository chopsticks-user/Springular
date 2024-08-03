import {Component, inject} from '@angular/core';
import {TransactionEditorComponent} from '@core/layouts/home/finance/transaction-editor/transaction-editor.component';
import {GroupEditorComponent} from '@core/layouts/home/finance/group-editor/group-editor.component';
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";
import {ConfirmationService} from "@shared/services/confirmation.service";
import {ConfirmationComponent} from "@core/layouts/dialog/confirmation/confirmation.component";
import {NotificationService} from "@shared/services/notification.service";
import {GroupViewComponent} from "@core/layouts/home/finance/group-view/group-view.component";
import {Router} from "@angular/router";
import {DateTimeService} from "@features/home/calendar/date-time.service";
import {AsyncPipe} from "@angular/common";
import {FinanceService} from "@features/home/finance/finance.service";
import {toSignal} from "@angular/core/rxjs-interop";
import {TransactionGroup} from "@shared/domain/types";
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-home-finance-group-explorer',
  standalone: true,
  imports: [
    TransactionEditorComponent,
    GroupEditorComponent,
    ModalComponent,
    ConfirmationComponent,
    GroupViewComponent,
    AsyncPipe,
    IconComponent,
  ],
  templateUrl: './group-explorer.component.html',
  styleUrl: './group-explorer.component.css',
})
export class GroupExplorerComponent {
  private _confirmationService = inject(ConfirmationService);
  private _notificationService = inject(NotificationService);
  private _dateTimeService = inject(DateTimeService);
  private _financeService = inject(FinanceService);
  private _router = inject(Router);

  public groupEditorShouldOpen = false;
  public transactionEditorShouldOpen = false;
  public selectedGroup?: TransactionGroup;
  public readonly months: string[] = this._dateTimeService.months;
  public readonly years: number[] = this._dateTimeService.years;
  public readonly today: Date = new Date();

  public rootGroup$ = toSignal(this._financeService.rootGroup$);
  public childrenGroups$ = this._financeService.childrenGroups$;

  public goBack(): void {
    const path: string | undefined = this.rootGroup$()?.path
    if (path && path === '/') {
      return;
    }
    void this._router.navigateByUrl(`${this._router.url.substring(
      0, this._router.url.lastIndexOf('/')
    )}`);
  }

  public editTransactionGroup(): void {
    if (this.rootGroup$()?.path !== '/') {
      this.groupEditorShouldOpen = true;
      this.selectedGroup = this.rootGroup$();
    }
  }

  public closeGroupEditor(): void {
    this.selectedGroup = undefined;
    this.groupEditorShouldOpen = false;
  }

  public displayedPath(path: string): string {
    return path.substring(1);
  }

  public actualPath(path: string): string {
    return '/'.concat(path);
  }
}
