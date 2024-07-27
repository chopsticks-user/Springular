import {Component, inject, input, output} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormControlErrorDictionary} from "@shared/domain/types";
import {FormService} from "@shared/services/form.service";

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
  private _formService = inject(FormService);

  public submitButtonName = input<string>('submit');
  public formGroup = input.required<FormGroup>();
  public errorDictionaries = input<FormControlErrorDictionary[]>([]);
  public submitted = output<void>();

  public errorMessage = '';

  public submitHandler(): void {
    if (this.formGroup().invalid) {
      // TODO: this statement might have no effect
      this.formGroup().markAllAsTouched();
      this.errorMessage = this._formService.getErrorMessage(
        this.formGroup(),
        this.errorDictionaries()
      );
      return;
    }
    
    this.submitted.emit();
  }
}
