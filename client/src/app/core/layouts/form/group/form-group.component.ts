import {Component, input, output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-layout-form-group',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './form-group.component.html',
  styleUrl: './form-group.component.css'
})
export class FormGroupComponent {
  public submitButtonName = input<string>('submit');
  public formGroup = input.required<FormGroup>();
  public submitted = output<any>();

  public status = '';
}
