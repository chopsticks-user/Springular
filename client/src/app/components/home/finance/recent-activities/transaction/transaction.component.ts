import { Component, inject, input } from '@angular/core';
import { Transaction } from '@shared/types';

@Component({
  selector: 'app-home-finance-recent-activities-transaction',
  standalone: true,
  imports: [],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css',
})
export class TransactionComponent {
  public transaction = input.required<Transaction>();
}
