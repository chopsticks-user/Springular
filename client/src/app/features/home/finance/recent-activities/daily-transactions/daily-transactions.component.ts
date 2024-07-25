import {Component} from '@angular/core';
import {TransactionComponent} from "../transaction/transaction.component";

@Component({
  selector: 'app-home-finance-daily-transactions',
  standalone: true,
  imports: [
    TransactionComponent
  ],
  templateUrl: './daily-transactions.component.html',
  styleUrl: './daily-transactions.component.css'
})
export class DailyTransactionsComponent {

}
