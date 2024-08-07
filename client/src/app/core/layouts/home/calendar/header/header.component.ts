import {AsyncPipe} from '@angular/common';
import {Component, inject, output} from '@angular/core';
import {CalendarWeekViewComponent} from '../week-view/week-view.component';
import {DateTime} from 'luxon';
import {Observable} from 'rxjs';
import {DateTimeService} from '@features/home/calendar/date-time.service';
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-home-calendar-header',
  standalone: true,
  imports: [CalendarWeekViewComponent, AsyncPipe, IconComponent],
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
