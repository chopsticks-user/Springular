import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { CalendarEvent } from '@shared/types';
import { Observable, map, startWith } from 'rxjs';
import { MatSelectModule } from '@angular/material/select';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-shared-calendar-event-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    AsyncPipe,
  ],
  templateUrl: './calendar-event-dialog.component.html',
  styleUrl: './calendar-event-dialog.component.css',
})
export class CalendarEventDialogComponent {
  @Input() public calendarEvent$!: Observable<CalendarEvent>;
  @Output() public $cancelButtonClicked = new EventEmitter();
  @Output() public $submitButtonClicked = new EventEmitter();

  public eventFormGroup: FormGroup = new FormGroup({
    title: new FormControl<string>('', [Validators.required]),
    description: new FormControl<string>('', [Validators.required]),
    start: new FormControl<string>('', [Validators.required]),
    end: new FormControl<string>('', [Validators.required]),
    color: new FormControl<string>('green', [Validators.required]),
    repeat: new FormControl<string>('None', [Validators.required]),
  });

  public get repeatEveryEnabled$(): Observable<boolean> {
    return this.eventFormGroup.get('repeat')?.valueChanges.pipe(
      startWith(this.eventFormGroup.get('repeat')?.value),
      map((value: string | null) => {
        if (!value || value.toLowerCase() !== 'custom') {
          this.eventFormGroup.removeControl('repeatEvery');
          return false;
        }

        this.eventFormGroup.addControl(
          'repeatEvery',
          new FormControl<string>('', [Validators.required])
        );
        return true;
      })
    ) as Observable<boolean>;
  }

  public submitEvent(): void {
    if (this.eventFormGroup.valid) {
      const calendarEvent: CalendarEvent = {
        title: this.eventFormGroup.get('title')?.value,
        description: this.eventFormGroup.get('description')?.value,
        color: this.eventFormGroup.get('color')?.value,
        start: this.eventFormGroup.get('start')?.value,
        durationMinutes: 60,
        repeat: this.eventFormGroup.get('repeat')?.value,
      };

      if (this.eventFormGroup.contains('repeatEvery')) {
        calendarEvent.repeatEvery = {
          value: 60,
          unit: this.eventFormGroup.get('repeatEvery')?.value,
        };
      }

      return console.log(calendarEvent);
    }

    console.log('Invalid form');
  }
}
