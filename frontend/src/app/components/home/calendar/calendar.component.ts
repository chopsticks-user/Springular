import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { CalendarWeekViewComponent } from './week-view/week-view.component';
import { CalendarHeaderComponent } from './header/header.component';
import { CalendarEventEditorComponent } from '@shared/calendar-event-editor/calendar-event-editor.component';
import { DateTime, Info } from 'luxon';
import { Observable, map } from 'rxjs';
import {
  CalendarEvent,
  CalendarWeekDay,
  EventEditorTypes,
} from '@shared/types';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { CalendarEventsService } from '@services/calendar-events.service';

@Component({
  selector: 'app-home-calendar',
  standalone: true,
  imports: [
    CalendarWeekViewComponent,
    AsyncPipe,
    MatIcon,
    CalendarHeaderComponent,
    CalendarEventEditorComponent,
  ],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent implements OnInit {
  @ViewChild('eventEditorModal', { static: true })
  private _eventEditorModal!: ElementRef<HTMLDialogElement>;

  private _calendarEventsService = inject(CalendarEventsService);
  private _selectedCalendarEvent: CalendarEvent | null = null;
  private _eventEditorVisible: boolean = false;
  private _eventEditorType: EventEditorTypes = 'add';

  public calendarEvents$ = this._calendarEventsService.calendarEvents$;

  ngOnInit(): void {
    this._calendarEventsService.load('week');
  }

  public get today$(): Observable<DateTime> {
    return this._calendarEventsService.today$;
  }

  public get currentTime$(): Observable<DateTime> {
    return this._calendarEventsService.currentTime$;
  }

  public get firstDayOfWeek$(): Observable<DateTime> {
    return this._calendarEventsService.firstDayOfWeek$;
  }

  public get lastDayOfWeek$(): Observable<DateTime> {
    return this._calendarEventsService.lastDayOfWeek$;
  }

  public get weekDays$(): Observable<CalendarWeekDay[]> {
    return this._calendarEventsService.currentWeekDays$;
  }

  // public get calendarEvents$(): Observable<CalendarEvent[]> {
  //   return this._calendarEventsService.calendarEvents$;
  // }

  public get eventEditorVisible(): boolean {
    return this._eventEditorVisible;
  }

  public get selectedCalendarEvent(): CalendarEvent | null {
    return this._selectedCalendarEvent;
  }

  public get eventEditorType(): EventEditorTypes {
    return this._eventEditorType;
  }

  public get todayString$(): Observable<string> {
    return this.today$.pipe(
      map((today) => today.toLocaleString(DateTime.DATETIME_MED))
    );
  }

  public onTodayButtonHovered() {
    this._calendarEventsService.updateToday();
  }

  public onTodayButtonClicked() {
    this._calendarEventsService.resetCurrentTime();
  }

  public onNextButtonClicked() {
    this._calendarEventsService.currentTimeToNextWeek();
  }

  public onPrevButtonClicked() {
    this._calendarEventsService.currentTimeToLastWeek();
  }

  public openEventEditor(calendarEvent?: CalendarEvent) {
    if (calendarEvent) {
      this._eventEditorType = 'edit';
      this._selectedCalendarEvent = calendarEvent;
    } else {
      this._eventEditorType = 'add';
      this._selectedCalendarEvent = null;
    }

    this._eventEditorVisible = true;
    this._eventEditorModal.nativeElement.showModal();
  }

  public closeEventEditor(): void {
    this._eventEditorVisible = false;
    this._eventEditorModal.nativeElement.close();
  }

  public deleteCalendarEvent(calendarEvent: CalendarEvent): void {
    this._calendarEventsService
      .deleteCalendarEvent(calendarEvent)
      .subscribe(() => this.closeEventEditor());
  }

  public submitCalendarEvent(calendarEvent: CalendarEvent): void {
    switch (this._eventEditorType) {
      case 'add': {
        this._calendarEventsService.addCalendarEvent(calendarEvent).subscribe({
          next: () => this.closeEventEditor(),
          error: () => console.log('error'),
        });
        return;
      }
      case 'edit': {
        this._calendarEventsService.editCalendarEvent(calendarEvent).subscribe({
          next: () => this.closeEventEditor(),
          error: () => console.log('error'),
        });
        return;
      }
    }
  }
}
