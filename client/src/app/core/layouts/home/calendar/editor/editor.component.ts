import {Component, inject, input, OnInit, output, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {
  CalendarEvent,
  CalendarEventRepeat,
  CalendarEventRepeatEveryUnit,
  calendarEventRepeatEveryUnits,
  calendarEventRepeatOptions,
  FormControlErrorDictionary,
} from '@shared/domain/types';
import {map, Observable, startWith} from 'rxjs';
import {AsyncPipe} from '@angular/common';
import {DateTime} from 'luxon';
import {GroupComponent} from "@core/layouts/form/group/group.component";
import {FieldComponent} from "@core/layouts/form/field/field.component";
import {CalendarEventsService} from "@features/home/calendar/calendar-events.service";
import {HttpErrorResponse} from "@angular/common/http";
import {divisibleByValidator} from "@shared/directives/validators/divisible-by.validator";
import {divisibleByMinutesValidator} from "@shared/directives/validators/divisible-by-minutes.validator";

@Component({
  selector: 'app-layout-home-calendar-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatIconModule,
    AsyncPipe,
    GroupComponent,
    FieldComponent,
  ],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css',
})
export class EditorComponent implements OnInit {
  private _calendarEventsService = inject(CalendarEventsService);

  public calendarEvent = input<CalendarEvent>();
  public editorShouldClose = output<void>();

  public readonly repeatOptions = calendarEventRepeatOptions;
  public readonly repeatEveryUnits = calendarEventRepeatEveryUnits;
  public readonly errorDictionaries: FormControlErrorDictionary[] = [
    {
      name: 'title',
      entries: [
        {type: 'required', message: 'Title is required'},
        {type: 'maxLength', message: 'Title must contain at most 20 characters'},
      ],
    },
    {
      name: 'description',
      entries: [
        {type: 'maxLength', message: 'Description must contain at most 100 characters'},
      ],
    },
    {
      name: 'start',
      entries: [
        {type: 'required', message: 'Start time is required'},
        {type: 'divisibleByMinutes', message: 'Start time minutes must be divisible by 5'},
      ],
    },
    {
      name: 'duration',
      entries: [
        {type: 'required', message: 'Duration is required'},
        {type: 'divisibleBy', message: 'Duration minutes must be divisible by 5'},
      ],
    },
    {
      name: 'color',
      entries: [
        {type: 'required', message: 'Color is required'},
      ],
    },
    {
      name: 'repeat',
      entries: [
        {type: 'required', message: 'Repeat is required'},
      ],
    },
    {
      name: 'repeatEveryUnit',
      entries: [
        {type: 'required', message: 'Repeat interval unit is required'},
      ],
    },
    {
      name: 'repeatEveryValue',
      entries: [
        {type: 'required', message: 'Repeat interval value is required'},
      ],
    },
  ];
  public formGroup!: FormGroup;

  ngOnInit(): void {
    this.formGroup = new FormGroup({
      title: new FormControl<string>(
        this.calendarEvent()?.title || '',
        [
          Validators.required,
          Validators.maxLength(20),
        ],
      ),
      description: new FormControl<string>(
        this.calendarEvent()?.description || '',
        [Validators.maxLength(100)],
      ),
      start: new FormControl<string>(
        this._formatDate(this.calendarEvent()?.start) ||
        this._formatDate(DateTime.local().toJSDate()),
        [
          Validators.required,
          divisibleByMinutesValidator(5),
        ]
      ),
      duration: new FormControl<number>(
        this.calendarEvent()?.durationMinutes || 15,
        [
          Validators.required,
          divisibleByValidator(5),
        ]
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

      if (!submittedCalendarEvent.id) {
        this._calendarEventsService.addCalendarEvent(
          submittedCalendarEvent
        ).subscribe({
          next: () => this.editorShouldClose.emit(),
          error: (res: HttpErrorResponse) =>
            errorMessage.set(res.error.description),
        });
        return;
      } else {
        this._calendarEventsService.editCalendarEvent(
          submittedCalendarEvent
        ).subscribe({
          next: () => this.editorShouldClose.emit(),
          error: (res: HttpErrorResponse) =>
            errorMessage.set(res.error.description),
        });
        return;
      }
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
