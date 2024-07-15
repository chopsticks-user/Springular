import {
  Component,
  ElementRef,
  inject,
  OnInit,
  ViewChild,
} from '@angular/core';
import { CalendarWeekViewComponent } from './week-view/week-view.component';
import { CalendarHeaderComponent } from './header/header.component';
import { CalendarEventEditorComponent } from '@shared/calendar-event-editor/calendar-event-editor.component';
import { CalendarEvent, EventEditorTypes } from '@shared/types';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { CalendarEventsService } from '@services/calendar-events.service';
import { DateTimeService } from '@services/date-time.service';

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
  private _dateTimeService = inject(DateTimeService);

  private _selectedCalendarEvent: CalendarEvent | null = null;
  private _eventEditorVisible: boolean = false;
  private _eventEditorType: EventEditorTypes = 'add';

  public calendarEvents$ = this._calendarEventsService.calendarEvents$;

  ngOnInit(): void {
    this._dateTimeService.firstDayOfWeek$.subscribe((firstDayOfWeek) =>
      this._calendarEventsService.load('week', firstDayOfWeek)
    );
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
