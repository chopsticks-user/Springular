import {Component, input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';

@Component({
  selector: 'app-shared-form-field',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
  ],
  templateUrl: './form-field.component.html',
  styleUrl: './form-field.component.css',
})
export class FormFieldComponent {
  public label = input.required<string>();
  public control = input.required<FormControl>();
  public type = input<string>('input');
  public icon = input<string>();
  public color = input<string>('var(--foreground-light');
  public width = input<string>();
}
