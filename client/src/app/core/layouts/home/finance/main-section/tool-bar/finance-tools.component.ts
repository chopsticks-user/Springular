import {Component, ElementRef, output, ViewChild} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {AddTransactionComponent} from './add-transaction/add-transaction.component';
import {AddGroupComponent} from '@core/layouts/home/finance/main-section/tool-bar/add-group/add-group.component';
import {MatSuffix} from "@angular/material/form-field";

@Component({
  selector: 'app-layout-home-finance-tool-bar',
  standalone: true,
  imports: [
    MatIconModule,
    AddTransactionComponent,
    AddGroupComponent,
    MatSuffix,
  ],
  templateUrl: './finance-tools.component.html',
  styleUrl: './finance-tools.component.css',
})
export class FinanceToolsComponent {
  @ViewChild('addTransactionModal', {static: true})
  private _addTransactionModalRef!: ElementRef<HTMLDialogElement>;

  @ViewChild('addTransactionGroupModal', {static: true})
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
