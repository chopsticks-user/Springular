import { Component, Input } from '@angular/core';
import { CalendarEvent } from '@shared/types';

@Component({
  selector: 'app-shared-calendar-event',
  standalone: true,
  imports: [],
  templateUrl: './calendar-event.component.html',
  styleUrl: './calendar-event.component.css',
})
export class CalendarEventComponent {
  @Input({ required: true }) events!: CalendarEvent[];
  @Input({ required: true }) pixelsPerHour!: number;
  @Input({ required: true }) borderPixelWidth!: number;
  // @Input({ required: true }) onClick!: (event: any) => void;

  getStyle(event: CalendarEvent | undefined) {
    if (!event) {
      return '';
    }
    let pixelHeight: number = (event.durationMinutes / 60) * this.pixelsPerHour;
    pixelHeight += Math.floor(pixelHeight / this.pixelsPerHour);

    let pixelMargin: number =
      (event.start.getMinutes() / 60) * this.pixelsPerHour;

    console.log(pixelMargin);

    return `background-color: ${event.color}; height: ${pixelHeight}px; position: absolute; top: ${pixelMargin}px;`;
  }
}
