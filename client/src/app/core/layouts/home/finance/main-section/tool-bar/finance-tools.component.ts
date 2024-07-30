import {Component, inject, input, signal} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {TransactionEditorComponent} from '@core/layouts/home/finance/transaction-editor/transaction-editor.component';
import {GroupEditorComponent} from '@core/layouts/home/finance/group-editor/group-editor.component';
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";
import {ConfirmationService} from "@shared/services/confirmation.service";
import {ConfirmationComponent} from "@core/layouts/dialog/confirmation/confirmation.component";
import {NotificationService} from "@shared/services/notification.service";
import {GroupExplorerComponent} from "@core/layouts/home/finance/main-section/group-explorer/group-explorer.component";
import {TransactionGroup} from "@shared/domain/types";

@Component({
  selector: 'app-layout-home-finance-tool-bar',
  standalone: true,
  imports: [
    MatIconModule,
    TransactionEditorComponent,
    GroupEditorComponent,
    ModalComponent,
    ConfirmationComponent,
    GroupExplorerComponent,
  ],
  templateUrl: './finance-tools.component.html',
  styleUrl: './finance-tools.component.css',
})
export class FinanceToolsComponent {
  private _confirmationService = inject(ConfirmationService);
  private _notificationService = inject(NotificationService);

  public groupEditorShouldOpen = false;
  public transactionEditorShouldOpen = false;
  public childrenGroups = input.required<TransactionGroup[]>();
}
