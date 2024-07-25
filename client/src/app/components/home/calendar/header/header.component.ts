import {AsyncPipe} from '@angular/common';
import {Component, inject, output} from '@angular/core';
import {MatIcon} from '@angular/material/icon';
import {CalendarWeekViewComponent} from '../week-view/week-view.component';
import {DateTime} from 'luxon';
import {Observable} from 'rxjs';
import {DateTimeService} from '@services/date-time.service';
import {MatSuffix} from "@angular/material/form-field";

@Component({
  selector: 'app-home-calendar-header',
  standalone: true,
  imports: [CalendarWeekViewComponent, AsyncPipe, MatIcon, MatSuffix],
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

  public onTodayButtonClicked(): void {
    this._dateTimeService.resetCurrentTime();
  }

  public onNextButtonClicked(): void {
    this._dateTimeService.currentTimeToNextWeek();
  }

  public onPrevButtonClicked(): void {
    this._dateTimeService.currentTimeToLastWeek();
  }
}
