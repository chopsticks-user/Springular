import {Component, inject, input} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {TransactionEditorComponent} from '@core/layouts/home/finance/transaction-editor/transaction-editor.component';
import {GroupEditorComponent} from '@core/layouts/home/finance/group-editor/group-editor.component';
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";
import {ConfirmationService} from "@shared/services/confirmation.service";
import {ConfirmationComponent} from "@core/layouts/dialog/confirmation/confirmation.component";
import {NotificationService} from "@shared/services/notification.service";
import {TransactionGroup} from "@shared/domain/types";
import {GroupComponent} from "@core/layouts/home/finance/group/group.component";
import {Router} from "@angular/router";
import {Info} from "luxon";
import {DateTimeService} from "@features/home/calendar/date-time.service";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-layout-home-finance-tool-bar',
  standalone: true,
  imports: [
    MatIconModule,
    TransactionEditorComponent,
    GroupEditorComponent,
    ModalComponent,
    ConfirmationComponent,
    GroupComponent,
    AsyncPipe,
  ],
  templateUrl: './finance-tools.component.html',
  styleUrl: './finance-tools.component.css',
})
export class FinanceToolsComponent {
  private _confirmationService = inject(ConfirmationService);
  private _notificationService = inject(NotificationService);
  private _dateTimeService = inject(DateTimeService);
  private _router = inject(Router);
  // private

  public groupEditorShouldOpen = false;
  public transactionEditorShouldOpen = false;
  public readonly months = Info.months();
  public readonly years = [...Array(30).keys()]
    .map(value => value + 2000).reverse();
  public readonly today = new Date();
  public rootGroup = input.required<TransactionGroup | null>();
  public childrenGroups = input.required<TransactionGroup[]>();

  constructor() {
    console.log(this.today.getMonth());
  }

  public goBack(): void {
    const path = this.rootGroup()?.path
    if (path && path === '/') {
      return;
    }
    void this._router.navigateByUrl(`${this._router.url.substring(
      0, this._router.url.lastIndexOf('/')
    )}`);

  }
}
