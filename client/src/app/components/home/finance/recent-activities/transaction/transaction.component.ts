import {Component, input} from '@angular/core';
import {Transaction} from '@shared/types';
import {DateTime} from "luxon";

@Component({
  selector: 'app-home-finance-recent-activities-transaction',
  standalone: true,
  imports: [],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css',
})
export class TransactionComponent {
  public transaction = input.required<Transaction>();

  public get time(): string {
    return DateTime.fromJSDate(this.transaction().time)
      .toLocaleString(DateTime.TIME_SIMPLE);
  }
}
