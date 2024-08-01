import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Transaction, TransactionGroup} from '@shared/domain/types';
import {BehaviorSubject, filter, map, Observable, of, shareReplay, startWith, switchMap, tap} from 'rxjs';
import groupSample from '@core/layouts/home/finance/group-sample.json';
import {NavigationEnd, Router} from "@angular/router";

function fetchAllGroups(): Observable<TransactionGroup[]> {
  return of(groupSample);
}

function fetchAllPaths(): Observable<string[]> {
  return of(groupSample.map(group => group.path));
}

@Injectable({
  providedIn: 'root',
})
export class FinanceService {
  private _http = inject(HttpClient);
  private _router = inject(Router);
  private readonly _financeRootUrl = '/home/finance';
  private $transactions = new BehaviorSubject<Transaction[]>([]);
  private $groups = new BehaviorSubject<TransactionGroup[]>([]);

  public transactions$: Observable<Transaction[]> = this.$transactions.asObservable();
  public groups$: Observable<TransactionGroup[]> = this.$groups.asObservable();
  public path$: Observable<string> = this._router.events.pipe(
    filter((event): event is NavigationEnd => event instanceof NavigationEnd),
    map(event => event.url),
    filter(url => url.startsWith(this._financeRootUrl)),
    map(url => this.getStartPath(url)),
    tap(path => console.log(path)),
    shareReplay(1),
    startWith(this.getStartPath()),
  );
  public rootGroup$: Observable<TransactionGroup | undefined> = this.path$.pipe(
    switchMap(path => fetchAllGroups().pipe(
      map(groups =>
        groups.find(group => group.path === path) || undefined
      ),
    ))
  );
  public childrenGroups$: Observable<TransactionGroup[]> = this.path$.pipe(
    switchMap(path => this.rootGroup$.pipe(
        switchMap(
          rootGroup => fetchAllGroups().pipe(
            map(groups => groups
              .filter(group =>
                group.path.indexOf(path) === 0
                && !group.path.substring(path.padEnd(2, '/').length + 1).includes('/')
                && group.path !== path
              )
            ),
          )
        ),
      ),
    )
  );

  private getStartPath(url?: string): string {
    const path = url || this._router.url;
    if (!path.startsWith(this._financeRootUrl)) {
      return '/';
    }
    return path.substring(this._financeRootUrl.length).padEnd(1, '/');
  }

  // public load(interval: string, start: DateTime): void {
  // zip(
  //   this._http.get<Transaction[]>('/finance/transactions', {
  //     params: {
  //       interval: interval,
  //       start: start.toJSDate().toISOString(),
  //     },
  //   }),
  //   this._http.get<TransactionGroup[]>('/finance/groups')
  // ).subscribe(([transactions, groups]) => {
  //   this.$transactions.next(transactions);
  //   this.$groups.next(groups);
  // });
  // }

  // public getGroupById(id: string): Observable<TransactionGroup | undefined> {
  //   return this.$groups.pipe(
  //     map((groups) => groups.find((group) => group.id === id))
  //   );
  // }
}
