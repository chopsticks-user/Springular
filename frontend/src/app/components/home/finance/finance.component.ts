import { Component } from '@angular/core';
import { BalanceSummaryComponent } from './balance-summary/balance-summary.component';
import { BalanceDetailsComponent } from './balance-details/balance-details.component';
import { RecentTransactionsComponent } from './recent-transactions/recent-transactions.component';

@Component({
  selector: 'app-finance',
  standalone: true,
  imports: [
    BalanceSummaryComponent,
    BalanceDetailsComponent,
    RecentTransactionsComponent,
  ],
  templateUrl: './finance.component.html',
  styleUrl: './finance.component.css',
})
export class FinanceComponent {}
