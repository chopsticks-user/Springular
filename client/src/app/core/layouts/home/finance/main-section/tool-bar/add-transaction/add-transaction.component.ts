import {Component, input, output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {FieldComponent} from '@core/layouts/form/field/field.component';
import {Transaction, TransactionGroup} from '@shared/domain/types';

@Component({
  selector: 'app-layout-home-tool-bar-add-transaction',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    FieldComponent,
  ],
  templateUrl: './add-transaction.component.html',
  styleUrl: './add-transaction.component.css',
})
export class AddTransactionComponent {
  public availableGroups = input<TransactionGroup[]>([]);
  public onCloseModal = output<void>();

  public formGroup = new FormGroup({
    revenues: new FormControl<number>(0, [Validators.required]),
    expenses: new FormControl<number>(0, [Validators.required]),
    time: new FormControl<string>('', [Validators.required]),
    note: new FormControl<string>('', [Validators.required]),
    group: new FormControl<string>('', [Validators.required]),
  });

  public submit(): void {
    if (!this.formGroup.valid) {
      return;
    }

    const transaction: Transaction = {
      revenues: this.formGroup.get('revenues')?.value as number,
      expenses: this.formGroup.get('expenses')?.value as number,
      time: new Date(this.formGroup.get('time')?.value as string),
      note: this.formGroup.get('note')?.value as string,
      groupId: this.availableGroups().find(
        (group) => group.name === this.formGroup.get('groupName')?.value
      )!.id!,
    };

    console.log(transaction);
  }
}
