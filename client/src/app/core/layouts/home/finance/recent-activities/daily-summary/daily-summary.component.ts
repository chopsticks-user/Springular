import {Component} from '@angular/core';
import {
  TransactionComponent
} from "@core/layouts/home/finance/recent-activities/daily-summary/transaction/transaction.component";

@Component({
  selector: 'app-layout-home-finance-daily-summary',
  standalone: true,
  imports: [
    TransactionComponent
  ],
  templateUrl: './daily-summary.component.html',
  styleUrl: './daily-summary.component.css'
})
export class DailySummaryComponent {

}
