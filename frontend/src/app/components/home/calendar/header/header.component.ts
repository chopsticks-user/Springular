import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { CalendarWeekViewComponent } from '../week-view/week-view.component';
import { Observable } from 'rxjs';
import { CalendarWeekDay } from '@shared/types';
import { DateTime } from 'luxon';

@Component({
  selector: 'app-home-calendar-header',
  standalone: true,
  imports: [CalendarWeekViewComponent, AsyncPipe, MatIcon],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class CalendarHeaderComponent {
  @Input({ required: true }) public today!: string;
  @Input({ required: true }) public firstWeekday!: DateTime | null;
  @Input({ required: true }) public lastWeekday!: DateTime | null;

  @Output() public todayButtonHovered = new EventEmitter<void>();
  @Output() public todayButtonClicked = new EventEmitter<void>();
  @Output() public nextButtonClicked = new EventEmitter<void>();
  @Output() public prevButtonClicked = new EventEmitter<void>();
}
