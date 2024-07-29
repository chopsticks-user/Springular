import {Component} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {TransactionEditorComponent} from '@core/layouts/home/finance/transaction-editor/transaction-editor.component';
import {GroupEditorComponent} from '@core/layouts/home/finance/group-editor/group-editor.component';
import {ModalComponent} from "@core/layouts/modal/modal.component";

@Component({
  selector: 'app-layout-home-finance-tool-bar',
  standalone: true,
  imports: [
    MatIconModule,
    TransactionEditorComponent,
    GroupEditorComponent,
    ModalComponent,
  ],
  templateUrl: './finance-tools.component.html',
  styleUrl: './finance-tools.component.css',
})
export class FinanceToolsComponent {
  public groupEditorShouldOpen = false;
  public transactionEditorShouldOpen = false;
}
