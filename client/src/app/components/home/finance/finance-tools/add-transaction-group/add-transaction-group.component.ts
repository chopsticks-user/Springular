import { Component, output } from '@angular/core';

@Component({
  selector: 'app-home-finance-tools-add-transaction-group',
  standalone: true,
  imports: [],
  templateUrl: './add-transaction-group.component.html',
  styleUrl: './add-transaction-group.component.css',
})
export class AddTransactionGroupComponent {
  public onCloseModal = output<void>();
}
