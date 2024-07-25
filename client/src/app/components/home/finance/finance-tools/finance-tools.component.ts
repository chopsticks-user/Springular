import {Component, ElementRef, output, ViewChild} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {AddTransactionComponent} from './add-transaction/add-transaction.component';
import {AddTransactionGroupComponent} from './add-transaction-group/add-transaction-group.component';

@Component({
  selector: 'app-home-finance-tools',
  standalone: true,
  imports: [
    MatIconModule,
    AddTransactionComponent,
    AddTransactionGroupComponent,
  ],
  templateUrl: './finance-tools.component.html',
  styleUrl: './finance-tools.component.css',
})
export class FinanceToolsComponent {
  @ViewChild('addTransactionModal', { static: true })
  private _addTransactionModalRef!: ElementRef<HTMLDialogElement>;

  @ViewChild('addTransactionGroupModal', { static: true })
  private _addTransactionGroupModalRef!: ElementRef<HTMLDialogElement>;

  public onAddTransaction = output<void>();
  public onAddTransactionGroup = output<void>();
  public onSearch = output<void>();
  public onFilter = output<void>();

  public openTransactionModal(): void {
    this._addTransactionModalRef.nativeElement.showModal();
  }

  public closeTransactionModal(): void {
    this._addTransactionModalRef.nativeElement.close();
  }

  public openTransactionGroupModal(): void {
    this._addTransactionGroupModalRef.nativeElement.showModal();
  }

  public closeTransactionGroupModal(): void {
    this._addTransactionGroupModalRef.nativeElement.close();
  }
}
