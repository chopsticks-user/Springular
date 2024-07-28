import {Component, input, OnInit, output, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {
  CalendarEvent,
  CalendarEventRepeat,
  CalendarEventRepeatEveryUnit,
  calendarEventRepeatEveryUnits,
  calendarEventRepeatOptions,
} from '@shared/domain/types';
import {map, Observable, startWith} from 'rxjs';
import {MatSelectModule} from '@angular/material/select';
import {AsyncPipe} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {DateTime} from 'luxon';
import {GroupComponent} from "@core/layouts/form/group/group.component";
import {FieldComponent} from "@core/layouts/form/field/field.component";

@Component({
  selector: 'app-layout-home-calendar-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    AsyncPipe,
    MatButtonModule,
    GroupComponent,
    FieldComponent,
  ],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css',
})
export class EditorComponent implements OnInit {
  public calendarEvent = input<CalendarEvent>();
  public submitButtonClicked = output<CalendarEvent>();

  public readonly repeatOptions = calendarEventRepeatOptions;
  public readonly repeatEveryUnits = calendarEventRepeatEveryUnits;
  public formGroup!: FormGroup;

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      title: new FormControl<string>(this.calendarEvent()?.title || '', [
        Validators.required,
      ]),
      description: new FormControl<string>(
        this.calendarEvent()?.description || ''
      ),
      start: new FormControl<string>(
        this._formatDate(this.calendarEvent()?.start) ||
        this._formatDate(DateTime.local().toJSDate()),
        [Validators.required]
      ),
      duration: new FormControl<number>(
        this.calendarEvent()?.durationMinutes || 15,
        [Validators.required]
      ),
      color: new FormControl<string>(this.calendarEvent()?.color || 'green', [
        Validators.required,
      ]),
      repeat: new FormControl<CalendarEventRepeat>(
        this.calendarEvent()?.repeat || 'none',
        [Validators.required]
      ),
    });
  }

  public get repeatEveryEnabled$(): Observable<boolean> {
    return this.formGroup.get('repeat')?.valueChanges.pipe(
      startWith(this.formGroup.get('repeat')?.value),
      map((value: string | null) => {
        if (!value || value.toLowerCase() !== 'custom') {
          this.formGroup.removeControl('repeatEvery');
          return false;
        }

        this.formGroup.addControl(
          'repeatEveryUnit',
          new FormControl<CalendarEventRepeatEveryUnit>('weeks', [
            Validators.required,
          ])
        );
        this.formGroup.addControl(
          'repeatEveryValue',
          new FormControl<number>(1, [Validators.required])
        );
        return true;
      })
    ) as Observable<boolean>;
  }

  public submitHandler =
    (errorMessage: WritableSignal<string>): void => {
      const submittedCalendarEvent: CalendarEvent = {
        id: this.calendarEvent()?.id,
        title: this.formGroup.get('title')?.value as string,
        description: this.formGroup.get('description')?.value as string,
        color: this.formGroup.get('color')?.value as string,
        start: new Date(this.formGroup.get('start')?.value as string),
        durationMinutes: this.formGroup.get('duration')?.value as number,
        repeat: this.formGroup.get('repeat')?.value as CalendarEventRepeat,
      };

      if (
        this.formGroup.contains('repeatEveryValue') &&
        this.formGroup.contains('repeatEveryUnit')
      ) {
        submittedCalendarEvent.repeatEvery = {
          value: this.formGroup.get('repeatEveryValue')?.value as number,
          unit: this.formGroup.get('repeatEveryUnit')
            ?.value as CalendarEventRepeatEveryUnit,
        };
      }

      this.submitButtonClicked.emit(submittedCalendarEvent);
    }

  private _formatDate(date?: Date): string {
    if (!date) {
      date = new Date();
    } else {
      date = new Date(date);
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
