import {
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { CalendarWeekViewComponent } from './week-view/week-view.component';
import { CalendarHeaderComponent } from './header/header.component';
import { CalendarSidebarComponent } from './sidebar/sidebar.component';
import { CalendarEventEditorComponent } from '@shared/calendar-event-editor/calendar-event-editor.component';
import { DateTime, Info } from 'luxon';
import { BehaviorSubject, Observable, map, shareReplay, switchMap } from 'rxjs';
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
    CalendarSidebarComponent,
    CalendarEventEditorComponent,
  ],
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
})
export class CalendarComponent implements OnInit {
  @ViewChild('eventEditorModal', { static: true })
  private _eventEditorModal!: ElementRef<HTMLDialogElement>;

  private _calendarEventsService = inject(CalendarEventsService);
  private $today = new BehaviorSubject<DateTime>(DateTime.local());
  private $currentTime = new BehaviorSubject<DateTime>(DateTime.local());
  private _selectedCalendarEvent: CalendarEvent | null = null;
  private _eventEditorVisible: boolean = false;
  private _eventEditorType: EventEditorTypes = 'add';

  public currentFirstDayOfWeek$: Observable<DateTime> = this.$currentTime.pipe(
    map((currentTime) => {
      // console.log(currentTime.toISOWeekDate());
      return currentTime.startOf('week').toLocal();
    })
    // distinctUntilChanged(
    //   (prev, curr) => prev.toISOWeekDate() === curr.toISOWeekDate()
    // )
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

  public calendarEvents$: Observable<CalendarEvent[]> =
    this.currentFirstDayOfWeek$.pipe(
      switchMap((currentFirstDayOfWeek) =>
        this._calendarEventsService
          .getCalendarEvents('week', currentFirstDayOfWeek.toJSDate())
          .pipe(
            map((response) => {
              // if (response.body) {
              //   console.log(response.body[0].start.getMinutes());
              // }
              console.log(response.body);
              return response.body || [];
            })
          )
      ),
      shareReplay(1)
    );

  ngOnInit(): void {
    console.log('init');
  }

  public get eventEditorVisible(): boolean {
    return this._eventEditorVisible;
  }

  public get selectedCalendarEvent(): CalendarEvent | null {
    return this._selectedCalendarEvent;
  }

  public get eventEditorType(): EventEditorTypes {
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
    console.log('Deleting', calendarEvent);
    this.closeEventEditor();
  }

  public submitCalendarEvent(calendarEvent: CalendarEvent): void {
    switch (this._eventEditorType) {
      case 'add': {
        console.log('Adding', calendarEvent);
        break;
      }
      case 'edit': {
        console.log('Editing', calendarEvent);
        break;
      }
    }

    // if successful
    this.closeEventEditor();
  }
}
