import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {DateTime} from "luxon";
import {NotificationLevel} from "@shared/domain/types";


@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private readonly _defaultTimerDuration = 5000;
  private readonly _defaultLevel: NotificationLevel = 'info';

  private $message = new BehaviorSubject('');
  private $displayed = new BehaviorSubject(false);
  private $level =
    new BehaviorSubject<NotificationLevel>(this._defaultLevel);
  // private _currentTimer = new Subscription();

  public message$ = this.$message.asObservable();
  public displayed$ = this.$displayed.asObservable();
  public level$ = this.$level.asObservable();

  public show(
    message: string,
    level?: NotificationLevel,
    // duration?: number,
  ) {
    this.close();
    this.$message.next(
      `${DateTime.local().toLocaleString(DateTime.TIME_SIMPLE)}: ${message}`
    );
    this.$displayed.next(true);
    this.$level.next(this.notificationLevel(level));
    // this.resetTimer(duration);
  }

  // public clearTimer() {
  //   if (!this._currentTimer.closed) {
  //     console.log('clearing timer');
  //     this._currentTimer.unsubscribe();
  //   }
  // }

  // public resetTimer(duration?: number) {
  //   this.clearTimer();
  //   this._currentTimer = timer(this.timerDuration(duration)).subscribe(
  //     () => this.close()
  //   );
  // }

  public close() {
    // this.clearTimer();
    this.$displayed.next(false);
  }

  // private timerDuration(duration?: number) {
  //   return duration ? duration : this._defaultTimerDuration;
  // }

  private notificationLevel(level?: NotificationLevel) {
    return level || this._defaultLevel;
  }
}
