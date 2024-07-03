import { Component, ElementRef, ViewChild, getPlatform } from '@angular/core';
import { CalendarWeekViewComponent } from './week-view/week-view.component';
import { CalendarHeaderComponent } from './header/header.component';
import { CalendarSidebarComponent } from './sidebar/sidebar.component';
import { CalendarEventDialogComponent } from '@shared/calendar-event-dialog/calendar-event-dialog.component';
import { DateTime, Info } from 'luxon';
import { BehaviorSubject, Observable, Subject, map, of } from 'rxjs';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { OverlayContainer } from '@angular/cdk/overlay';

@Component({
  selector: 'app-home-calendar',
  standalone: true,
  imports: [
    CalendarWeekViewComponent,
    AsyncPipe,
    MatIcon,
    CalendarHeaderComponent,
    CalendarSidebarComponent,
    CalendarEventDialogComponent,
  ],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent {
  @ViewChild('eventEditorModal', { static: true })
  private _eventEditorModal!: ElementRef<HTMLDialogElement>;

  private $today = new BehaviorSubject<DateTime>(DateTime.local());
  private $currentTime = new BehaviorSubject<DateTime>(DateTime.local());

  private _selectedCalendarEvent: CalendarEvent | null = null;
  private _eventEditorVisible: boolean = false;
  private _eventEditorType: 'add' | 'edit' = 'add';

  public currentFirstDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => currentTime.startOf('week').toLocal())
  );
  public currentLastDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => currentTime.endOf('week').toLocal())
  );
  public currentWeekDays$: Observable<CalendarWeekDay[]> =
    this.currentFirstDayOfWeek$.pipe(
      map((currentFirstDayOfWeek) =>
        Info.weekdays('short').map((weekDayName, index) => ({
          dayOfWeek: weekDayName,
          dayOfMonth: currentFirstDayOfWeek.plus({ days: index }).day,
        }))
      )
    );
  public calendarEvents$ = of<CalendarEvent[]>(
    // todo: events should be sorted
    [
      {
        id: '0',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 28, 6, 30),
        durationMinutes: 30,
        repeat: 'none',
        color: 'green',
      },
      {
        id: '1',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 27, 15, 45),
        durationMinutes: 60,
        repeat: 'none',
        color: 'yellow',
      },
      {
        id: '2',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 28, 15, 15),
        durationMinutes: 15,
        repeat: 'none',
        color: 'red',
      },
      {
        id: '3',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 28, 15),
        durationMinutes: 5,
        repeat: 'none',
        color: 'blue',
      },
      {
        id: '4',
        title: 'Event',
        description: '',
        start: new Date(2024, 6, 27, 15, 5),
        durationMinutes: 10,
        repeat: 'none',
        color: 'orange',
      },
    ]
  );

  public get eventEditorVisible(): boolean {
    return this._eventEditorVisible;
  }

  public get selectedCalendarEvent(): CalendarEvent | null {
    return this._selectedCalendarEvent;
  }

  public get eventEditorType(): 'add' | 'edit' {
    return this._eventEditorType;
  }

  public get today(): Observable<string> {
    return this.$today.pipe(
      map((today) => today.toLocaleString(DateTime.DATETIME_MED))
    );
  }

  public onTodayButtonHovered() {
    this.$today.next(DateTime.local());
  }

  public onTodayButtonClicked() {
    this.$currentTime.next(DateTime.local());
  }

  public onNextButtonClicked() {
    this.$currentTime.next(this.$currentTime.value.plus({ days: 7 }));
  }

  public onPrevButtonClicked() {
    this.$currentTime.next(this.$currentTime.value.minus({ days: 7 }));
  }

  public openEventEditor(calendarEvent?: CalendarEvent) {
    if (calendarEvent) {
      this._eventEditorType = 'edit';
      this._selectedCalendarEvent = calendarEvent;
    } else {
      this._selectedCalendarEvent = null;
      this._eventEditorType = 'add';
    }

    this._eventEditorVisible = true;
    this._eventEditorModal.nativeElement.showModal();
  }

  public closeEventEditor() {
    console.log('close');
    this._eventEditorVisible = false;
    this._eventEditorModal.nativeElement.close();
  }

  public deleteCalendarEvent() {
    console.log('deleted');
    this._eventEditorVisible = false;
    this._eventEditorModal.nativeElement.close();
  }
}
