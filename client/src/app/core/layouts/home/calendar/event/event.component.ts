import {AsyncPipe} from '@angular/common';
import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CalendarEvent} from '@shared/domain/types';

@Component({
  selector: 'app-layout-home-calendar-event',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './event.component.html',
  styleUrl: './event.component.css',
})
export class EventComponent {
  @Input({required: true}) public events!: CalendarEvent[];
  @Input({required: true}) public pixelsPerHour!: number;
  @Input({required: true}) public borderPixelWidth!: number;
  @Output() public eventClicked = new EventEmitter<CalendarEvent>();

  public getStyle(event: CalendarEvent) {
    let pixelHeight: number = (event.durationMinutes / 60) * this.pixelsPerHour;
    pixelHeight += Math.floor(pixelHeight / this.pixelsPerHour);

    let pixelMargin: number =
      (new Date(event.start).getMinutes() / 60) * this.pixelsPerHour;

    return `background-color: ${event.color};
      height: ${pixelHeight}px;
      position: absolute;
      top: ${pixelMargin}px;`;
  }
}
