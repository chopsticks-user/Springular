import { AsyncPipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  @Input({ required: true }) public today$!: Observable<string>;
  @Input({ required: true }) public firstWeekday$!: Observable<DateTime>;
  @Input({ required: true }) public lastWeekday$!: Observable<DateTime>;

  @Output() public $todayButtonHovered = new EventEmitter<void>();
  @Output() public $todayButtonClicked = new EventEmitter<void>();
  @Output() public $nextButtonClicked = new EventEmitter<void>();
  @Output() public $prevButtonClicked = new EventEmitter<void>();
}
