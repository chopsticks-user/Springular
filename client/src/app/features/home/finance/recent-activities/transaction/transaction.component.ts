import {Component} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatSuffix} from "@angular/material/form-field";

@Component({
  selector: 'app-home-finance-transaction',
  standalone: true,
  imports: [
    MatIcon,
    MatSuffix
  ],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css',
})
export class TransactionComponent {
  // public transaction = input.required<Transaction>();
  //
  // public get time(): string {
  //   return DateTime.fromJSDate(this.transaction().time)
  //     .toLocaleString(DateTime.TIME_SIMPLE);
  // }
}
