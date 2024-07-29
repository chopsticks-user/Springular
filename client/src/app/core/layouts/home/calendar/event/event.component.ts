import {AsyncPipe} from '@angular/common';
import {Component, input, output} from '@angular/core';
import {CalendarEvent} from '@shared/domain/types';

@Component({
  selector: 'app-layout-home-calendar-event',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './event.component.html',
  styleUrl: './event.component.css',
})
export class EventComponent {
  public events = input.required <CalendarEvent[]>();
  public pixelsPerHour = input.required<number>();
  public borderPixelWidth = input.required<number>();
  public eventClicked = output<CalendarEvent>();

  public getStyle(event: CalendarEvent) {
    let pixelHeight: number =
      (event.durationMinutes / 60) * this.pixelsPerHour();
    pixelHeight += Math.floor(pixelHeight / this.pixelsPerHour());

    let pixelMargin: number =
      (new Date(event.start).getMinutes() / 60) * this.pixelsPerHour();

    return `background-color: ${event.color};
      height: ${pixelHeight}px;
      position: absolute;
      top: ${pixelMargin}px;`;
  }
}
