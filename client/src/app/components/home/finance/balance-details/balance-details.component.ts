import { Component } from '@angular/core';
import { FinanceToolsComponent } from '../finance-tools/finance-tools.component';

@Component({
  selector: 'app-home-finance-balance-details',
  standalone: true,
  imports: [FinanceToolsComponent],
  templateUrl: './balance-details.component.html',
  styleUrl: './balance-details.component.css',
})
export class BalanceDetailsComponent {}
