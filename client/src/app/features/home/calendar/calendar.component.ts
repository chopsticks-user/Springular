import {Component, inject, OnInit,} from '@angular/core';
import {CalendarWeekViewComponent} from '@core/layouts/home/calendar/week-view/week-view.component';
import {CalendarHeaderComponent} from '@core/layouts/home/calendar/header/header.component';
import {EditorComponent} from '@core/layouts/home/calendar/editor/editor.component';
import {CalendarEvent} from '@shared/domain/types';
import {AsyncPipe} from '@angular/common';
import {MatIcon} from '@angular/material/icon';
import {CalendarEventsService} from './calendar-events.service';
import {DateTimeService} from './date-time.service';
import {ModalComponent} from "@core/layouts/modal/modal.component";
import {ToolComponent} from "@core/layouts/modal/tool/tool.component";

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
    ToolComponent,
  ],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent implements OnInit {
  private _calendarEventsService = inject(CalendarEventsService);
  private _dateTimeService = inject(DateTimeService);

  public calendarEvents$ = this._calendarEventsService.calendarEvents$;
  public modalShouldOpen = false;
  public selectedCalendarEvent: CalendarEvent | undefined;

  ngOnInit(): void {
    this._dateTimeService.firstDayOfWeek$.subscribe((firstDayOfWeek) =>
      this._calendarEventsService.load('week', firstDayOfWeek)
    );
  }

  public openEventEditor(calendarEvent?: CalendarEvent) {
    this.selectedCalendarEvent = calendarEvent;
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
