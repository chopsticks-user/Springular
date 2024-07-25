import {Injectable} from '@angular/core';
import {CalendarWeekDay} from '@shared/domain/types';
import {DateTime, Info} from 'luxon';
import {BehaviorSubject, map, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DateTimeService {
  private $today = new BehaviorSubject<DateTime>(DateTime.local());
  private $currentTime = new BehaviorSubject<DateTime>(DateTime.local());

  public firstDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => {
      return currentTime.startOf('week').toLocal();
    })
  );

  public lastDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => currentTime.endOf('week').toLocal())
  );

  public currentWeekDays$: Observable<CalendarWeekDay[]> =
    this.firstDayOfWeek$.pipe(
      map((firstDayOfWeek) =>
        Info.weekdays('short').map((weekDayName, index) => ({
          dayOfWeek: weekDayName,
          dayOfMonth: firstDayOfWeek.plus({ days: index }).day,
        }))
      )
    );

  public get today$(): Observable<DateTime> {
    return this.$today;
  }

  public get todayString$(): Observable<string> {
    return this.today$.pipe(
      map((today) => today.toLocaleString(DateTime.DATETIME_MED))
    );
  }

  public get currentTime$(): Observable<DateTime> {
    return this.$currentTime;
  }

  public updateToday(): void {
    this.$today.next(DateTime.local());
  }

  public resetCurrentTime(): void {
    this.$currentTime.next(DateTime.local());
  }

  public currentTimeToNextWeek() {
    this.$currentTime.next(this.$currentTime.value.plus({ days: 7 }));
  }

  public currentTimeToLastWeek() {
    this.$currentTime.next(this.$currentTime.value.minus({ days: 7 }));
  }
}
