import {Component, inject, input, signal, WritableSignal} from '@angular/core';
import {FormGroup, ReactiveFormsModule} from "@angular/forms";
import {FormControlErrorDictionary} from "@shared/domain/types";
import {FormService} from "@shared/services/form.service";

@Component({
  selector: 'app-layout-form-group',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './group.component.html',
  styleUrl: './group.component.css'
})
export class GroupComponent {
  private _formService = inject(FormService);

  public submitButtonName = input<string>('submit');
  public formGroup = input.required<FormGroup>();
  public errorDictionaries = input<FormControlErrorDictionary[]>([]);
  public submitHandler =
    input.required<(errorMessageSignal: WritableSignal<string>) => void>();

  public errorMessageSignal = signal('');

  public submitRequestedHandler(): void {
    this.errorMessageSignal.set('');

    if (this.formGroup().invalid) {
      // TODO: this statement might have no effect
      this.formGroup().markAllAsTouched();
      this.errorMessageSignal.set(
        this._formService.getErrorMessage(
          this.formGroup(),
          this.errorDictionaries()
        )
      );
      console.log(this.errorMessageSignal());
      return;
    }

    this.submitHandler()(this.errorMessageSignal);
  }
}
