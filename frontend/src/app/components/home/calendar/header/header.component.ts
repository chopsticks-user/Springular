import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Input, output, Output } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { CalendarWeekViewComponent } from '../week-view/week-view.component';
import { DateTime } from 'luxon';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home-calendar-header',
  standalone: true,
  imports: [CalendarWeekViewComponent, AsyncPipe, MatIcon],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class CalendarHeaderComponent {
  @Input({ required: true }) public today!: string;
  @Input({ required: true }) public firstWeekday!: DateTime;
  @Input({ required: true }) public lastWeekday!: DateTime;

  public onTodayButtonHovered = output<void>();
  public onTodayButtonClicked = output<void>();
  public onNextButtonClicked = output<void>();
  public onPrevButtonClicked = output<void>();
  public onAddEventButtonClicked = output<void>();
}
