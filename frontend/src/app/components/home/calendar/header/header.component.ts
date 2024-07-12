import { AsyncPipe } from '@angular/common';
import {
  Component,
  EventEmitter,
  inject,
  Input,
  output,
  Output,
} from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { CalendarWeekViewComponent } from '../week-view/week-view.component';
import { DateTime } from 'luxon';
import { map, Observable } from 'rxjs';
import { DateTimeService } from '@services/date-time.service';

@Component({
  selector: 'app-home-calendar-header',
  standalone: true,
  imports: [CalendarWeekViewComponent, AsyncPipe, MatIcon],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class CalendarHeaderComponent {
  private _dateTimeService = inject(DateTimeService);

  public onAddEventButtonClicked = output<void>();
  public todayString$: Observable<string> = this._dateTimeService.todayString$;
  public firstWeekday$: Observable<DateTime> =
    this._dateTimeService.firstDayOfWeek$;
  public lastWeekday$: Observable<DateTime> =
    this._dateTimeService.lastDayOfWeek$;

  public onTodayButtonHovered(): void {
    this._dateTimeService.updateToday();
  }

  public onTodayButtonClicked() {
    this._dateTimeService.resetCurrentTime();
  }

  public onNextButtonClicked() {
    this._dateTimeService.currentTimeToNextWeek();
  }

  public onPrevButtonClicked() {
    this._dateTimeService.currentTimeToLastWeek();
  }
}
