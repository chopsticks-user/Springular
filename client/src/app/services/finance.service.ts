import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Transaction, TransactionGroup } from '@shared/types';
import { DateTime } from 'luxon';
import { BehaviorSubject, filter, map, Observable, zip } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FinanceService {
  private _http = inject(HttpClient);
  private $transactions = new BehaviorSubject<Transaction[]>([]);
  private $groups = new BehaviorSubject<TransactionGroup[]>([]);

  public transactions$: Observable<Transaction[]> = this.$transactions;
  public groups$: Observable<TransactionGroup[]> = this.$groups;

  public load(interval: string, start: DateTime): void {
    zip(
      this._http.get<Transaction[]>('/finance/transactions', {
        params: {
          interval: interval,
          start: start.toJSDate().toISOString(),
        },
      }),
      this._http.get<TransactionGroup[]>('/finance/groups')
    ).subscribe(([transactions, groups]) => {
      this.$transactions.next(transactions);
      this.$groups.next(groups);
    });
  }

  public getGroupById(id: string): Observable<TransactionGroup | undefined> {
    return this.$groups.pipe(
      map((groups) => groups.find((group) => group.id === id))
    );
  }
}
