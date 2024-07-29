import {Component} from '@angular/core';
import {ReactiveFormsModule,} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {FieldComponent} from '@core/layouts/form/mat-field/field.component';
import {GroupComponent} from "@core/layouts/form/group/group.component";

@Component({
  selector: 'app-layout-home-finance-transaction-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatIconModule,
    FieldComponent,
    GroupComponent,
  ],
  templateUrl: './transaction-editor.component.html',
  styleUrl: './transaction-editor.component.css',
})
export class TransactionEditorComponent {
  // public availableGroups = input<TransactionGroup[]>([]);
  // public onCloseModal = output<void>();
  //
  // public formGroup = new FormGroup({
  //   revenues: new FormControl<number>(0, [Validators.required]),
  //   expenses: new FormControl<number>(0, [Validators.required]),
  //   time: new FormControl<string>('', [Validators.required]),
  //   note: new FormControl<string>('', [Validators.required]),
  //   group: new FormControl<string>('', [Validators.required]),
  // });
  //
  // public submitHandler = (errorMessage: WritableSignal<string>): void => {
  //   const transaction: Transaction = {
  //     revenues: this.formGroup.get('revenues')?.value as number,
  //     expenses: this.formGroup.get('expenses')?.value as number,
  //     time: new Date(this.formGroup.get('time')?.value as string),
  //     note: this.formGroup.get('note')?.value as string,
  //     groupId: this.availableGroups().find(
  //       (group) => group.name === this.formGroup.get('groupName')?.value
  //     )!.id!,
  //   };
  //
  //   console.log(transaction);
  // }
}
