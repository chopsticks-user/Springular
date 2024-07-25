import {Component} from '@angular/core';
import {BalanceSummaryComponent} from './balance-summary/balance-summary.component';
import {BalanceDetailsComponent} from './balance-details/balance-details.component';
import {RecentActivitiesComponent} from './recent-activities/recent-activities.component';

@Component({
  selector: 'app-home-finance',
  standalone: true,
  imports: [
    BalanceSummaryComponent,
    BalanceDetailsComponent,
    RecentActivitiesComponent,
  ],
  templateUrl: './finance.component.html',
  styleUrl: './finance.component.css',
})
export class FinanceComponent {}
