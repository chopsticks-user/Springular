import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { CalendarEvent } from '@shared/types';
import { Observable } from 'rxjs';
import { MatSelect, MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-shared-calendar-event-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
  ],
  templateUrl: './calendar-event-dialog.component.html',
  styleUrl: './calendar-event-dialog.component.css',
})
export class CalendarEventDialogComponent {
  @Input() public calendarEvent!: Observable<CalendarEvent>;
  @Output() public $cancelButtonClicked = new EventEmitter();
  @Output() public $submitButtonClicked = new EventEmitter();

  public eventFormGroup: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    start: new FormControl('', [Validators.required]),
    end: new FormControl('', [Validators.required]),
    repeat: new FormControl('None', []),
  });

  public submitEvent(): void {
    console.log('submitted');
  }
}
