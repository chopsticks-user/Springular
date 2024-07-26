import {Component} from '@angular/core';
import {Transaction} from "@shared/domain/types";
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
  public readonly transactions: Transaction[] = [
    {
      id: 't0',
      time: new Date(),
      note: '',
      revenues: 100,
      expenses: 0,
      groupId: 'g0'
    },
    {
      id: 't1',
      time: new Date(),
      note: '',
      revenues: 100,
      expenses: 200,
      groupId: 'g0'
    },
    {
      id: 't2',
      time: new Date(),
      note: '',
      revenues: 100,
      expenses: 100,
      groupId: 'g0'
    }
  ];
}
