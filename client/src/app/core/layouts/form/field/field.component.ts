import {Component, input, output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-form-field',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    IconComponent
  ],
  templateUrl: './field.component.html',
  styleUrl: './field.component.css'
})
export class FieldComponent {
  public type = input<string>('text');
  public placeholder = input<string>();
  public formGroup = input.required<FormGroup>();
  public controlName = input.required<string>();
  public icon = input.required<string>();
  public title = input<string>();
  public iconClicked = output<void>();
}
