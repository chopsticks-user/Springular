import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { CalendarEvent, CalendarWeekDay } from '@shared/types';
import { CalendarEventComponent } from '@shared/calendar-event/calendar-event.component';
import { BehaviorSubject, Observable, map } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-home-calendar-week-view',
  standalone: true,
  imports: [CalendarEventComponent, AsyncPipe, MatIcon],
  templateUrl: './week-view.component.html',
  styleUrl: './week-view.component.css',
})
export class CalendarWeekViewComponent {
  @Input({ required: true }) public weekDays!: CalendarWeekDay[];
  @Input({ required: true }) public calendarEvents!: CalendarEvent[];
  @Output() public $eventClicked = new EventEmitter<CalendarEvent>();

  public readonly hours: number[] = [...Array(24).keys()];
  public readonly hourTexts: string[] = this.hours.map((hourNumber) =>
    this.hourTotext(hourNumber)
  );

  public scheduledEvents(hour: number, dayOfMonth: number): CalendarEvent[] {
    return this.calendarEvents.filter(
      (event) =>
        new Date(event.start).getHours() === hour &&
        new Date(event.start).getDate() === dayOfMonth
    );
  }

  public onEventClicked(calendarEvent: CalendarEvent) {
    this.$eventClicked.emit(calendarEvent);
  }

  public hourTotext(hour: number): string {
    switch (hour) {
      case 0:
        return '12:00 AM';
      case 12:
        return '12:00 PM';
      default:
        return hour < 12 ? `${hour}:00 AM` : `${hour - 12}:00 PM`;
    }
  }
}
