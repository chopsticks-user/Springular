import {Component} from '@angular/core';
import {
  TransactionComponent
} from "@core/layouts/home/finance/recent-activities/daily-summary/transaction/transaction.component";
import {
  DailySummaryComponent
} from "@core/layouts/home/finance/recent-activities/daily-summary/daily-summary.component";

@Component({
  selector: 'app-layout-home-finance-recent-activities',
  standalone: true,
  imports: [
    TransactionComponent,
    DailySummaryComponent
  ],
  templateUrl: './recent-activities.component.html',
  styleUrl: './recent-activities.component.css',
})
export class RecentActivitiesComponent {
}
