import {Component, inject, OnInit,} from '@angular/core';
import {CalendarWeekViewComponent} from '@core/layouts/home/calendar/week-view/week-view.component';
import {CalendarHeaderComponent} from '@core/layouts/home/calendar/header/header.component';
import {EditorComponent} from '@core/layouts/home/calendar/editor/editor.component';
import {CalendarEvent} from '@shared/domain/types';
import {AsyncPipe} from '@angular/common';
import {MatIcon} from '@angular/material/icon';
import {CalendarEventsService} from './calendar-events.service';
import {DateTimeService} from './date-time.service';
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";

@Component({
  selector: 'app-home-calendar',
  standalone: true,
  imports: [
    CalendarWeekViewComponent,
    AsyncPipe,
    MatIcon,
    CalendarHeaderComponent,
    EditorComponent,
    ModalComponent,
  ],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent implements OnInit {
  private _calendarEventsService = inject(CalendarEventsService);
  private _dateTimeService = inject(DateTimeService);

  private _selectedCalendarEvent: CalendarEvent | null = null;

  public calendarEvents$ = this._calendarEventsService.calendarEvents$;
  public modalShouldOpen = false;

  ngOnInit(): void {
    this._dateTimeService.firstDayOfWeek$.subscribe((firstDayOfWeek) =>
      this._calendarEventsService.load('week', firstDayOfWeek)
    );
  }


  public get selectedCalendarEvent(): CalendarEvent | null {
    return this._selectedCalendarEvent;
  }

  public openEventEditor(calendarEvent?: CalendarEvent) {
    if (calendarEvent) {
      this._selectedCalendarEvent = calendarEvent;
    } else {
      this._selectedCalendarEvent = null;
    }

    this.modalShouldOpen = true;
  }

  public closeEventEditor(): void {
    this.modalShouldOpen = false;
  }

  public deleteCalendarEvent(calendarEvent: CalendarEvent): void {
    this._calendarEventsService
      .deleteCalendarEvent(calendarEvent)
      .subscribe(() => this.closeEventEditor());
  }

  public submitCalendarEvent(calendarEvent: CalendarEvent): void {
    if (!calendarEvent.id) {
      this._calendarEventsService.addCalendarEvent(calendarEvent)
        .subscribe({
          next: () => this.closeEventEditor(),
          error: () => console.log('error'),
        });
      return;
    } else {
      this._calendarEventsService.editCalendarEvent(calendarEvent)
        .subscribe({
          next: () => this.closeEventEditor(),
          error: () => console.log('error'),
        });
      return;
    }
  }
}
