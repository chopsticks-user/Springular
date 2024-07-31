import {Component} from '@angular/core';
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-home-finance-transaction',
  standalone: true,
  imports: [
    IconComponent
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
