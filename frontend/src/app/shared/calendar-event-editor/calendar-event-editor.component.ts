import { Component, EventEmitter, Input, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {
  CalendarEvent,
  CalendarEventRepeat,
  CalendarEventRepeatEveryUnit,
  EventEditorTypes,
} from '@shared/types';
import { Observable, map, startWith } from 'rxjs';
import { MatSelectModule } from '@angular/material/select';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { DateTime } from 'luxon';

@Component({
  selector: 'app-shared-calendar-event-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    AsyncPipe,
    MatButtonModule,
  ],
  templateUrl: './calendar-event-editor.component.html',
  styleUrl: './calendar-event-editor.component.css',
})
export class CalendarEventEditorComponent {
  @Input({ required: true }) public type!: EventEditorTypes;
  @Input({ required: true }) public calendarEvent!: CalendarEvent | null;
  @Output() public $cancelButtonClicked = new EventEmitter<void>();
  @Output() public $submitButtonClicked = new EventEmitter<CalendarEvent>();
  @Output() public $deleteButtonClicked = new EventEmitter<CalendarEvent>();

  public eventFormGroup: FormGroup = new FormGroup({
    title: new FormControl<string>('', [Validators.required]),
    description: new FormControl<string>('', [Validators.required]),
    start: new FormControl<string>(
      this._formatDate(DateTime.local().toJSDate()),
      [Validators.required]
    ),
    duration: new FormControl<number>(15, [Validators.required]),
    color: new FormControl<string>('#7cfc00', [Validators.required]),
    repeat: new FormControl<CalendarEventRepeat>('none', [Validators.required]),
  });

  ngOnChanges(): void {
    if (this.type === 'edit') {
      this.eventFormGroup = new FormGroup({
        title: new FormControl<string>(this.calendarEvent?.title || '', [
          Validators.required,
        ]),
        description: new FormControl<string>(
          this.calendarEvent?.description || '',
          [Validators.required]
        ),
        start: new FormControl<string>(
          this._formatDate(this.calendarEvent?.start) ||
            this._formatDate(DateTime.local().toJSDate()),
          [Validators.required]
        ),
        duration: new FormControl<number>(
          this.calendarEvent?.durationMinutes || 15,
          [Validators.required]
        ),
        color: new FormControl<string>(this.calendarEvent?.color || 'green', [
          Validators.required,
        ]),
        repeat: new FormControl<CalendarEventRepeat>(
          this.calendarEvent?.repeat || 'none',
          [Validators.required]
        ),
      });
    }
  }

  public get repeatEveryEnabled$(): Observable<boolean> {
    return this.eventFormGroup.get('repeat')?.valueChanges.pipe(
      startWith(this.eventFormGroup.get('repeat')?.value),
      map((value: string | null) => {
        if (!value || value.toLowerCase() !== 'custom') {
          this.eventFormGroup.removeControl('repeatEvery');
          return false;
        }

        this.eventFormGroup.addControl(
          'repeatEveryUnit',
          new FormControl<CalendarEventRepeatEveryUnit>('weeks', [
            Validators.required,
          ])
        );
        this.eventFormGroup.addControl(
          'repeatEveryValue',
          new FormControl<number>(1, [Validators.required])
        );
        return true;
      })
    ) as Observable<boolean>;
  }

  public submitEvent(): void {
    if (!this.eventFormGroup.valid) {
      // todo:
      return;
    }

    const calendarEvent: CalendarEvent = {
      title: this.eventFormGroup.get('title')?.value as string,
      description: this.eventFormGroup.get('description')?.value as string,
      color: this.eventFormGroup.get('color')?.value as string,
      start: new Date(this.eventFormGroup.get('start')?.value as string),
      durationMinutes: this.eventFormGroup.get('duration')?.value as number,
      repeat: this.eventFormGroup.get('repeat')?.value as CalendarEventRepeat,
    };

    if (
      this.eventFormGroup.contains('repeatEveryValue') &&
      this.eventFormGroup.contains('repeatEveryUnit')
    ) {
      calendarEvent.repeatEvery = {
        value: this.eventFormGroup.get('repeatEveryValue')?.value as number,
        unit: this.eventFormGroup.get('repeatEveryUnit')
          ?.value as CalendarEventRepeatEveryUnit,
      };
    }

    this.$submitButtonClicked.emit(calendarEvent);
  }

  private _formatDate(date?: Date): string {
    if (!date) {
      date = new Date();
    }

    const padZero = (num: number) => (num < 10 ? `0${num}` : `${num}`);

    const year = date.getFullYear();
    const month = padZero(date.getMonth() + 1);
    const day = padZero(date.getDate());
    const hours = padZero(date.getHours());
    const minutes = padZero(date.getMinutes());
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  }
}
