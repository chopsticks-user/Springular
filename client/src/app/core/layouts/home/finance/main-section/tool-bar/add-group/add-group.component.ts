import {Component, input, output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {FieldComponent} from '@core/layouts/form/field/field.component';
import {TransactionGroup} from '@shared/domain/types';

@Component({
  selector: 'app-layout-home-tool-bar-add-group',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatButtonModule,
    FieldComponent,
  ],
  templateUrl: './add-group.component.html',
  styleUrl: './add-group.component.css',
})
export class AddGroupComponent {
  public availableGroups = input<TransactionGroup[]>([]);
  public onCloseModal = output<void>();

  public formGroup = new FormGroup({
    name: new FormControl<string>('', [Validators.required]),
    description: new FormControl<string>('', []),
    color: new FormControl<string>('#7cfc00', [Validators.required]),
    parent: new FormControl<string | null>(null, []),
  });

  public submit(): void {
    if (!this.formGroup.valid) {
      return;
    }

    const transactionGroup: TransactionGroup = {
      revenues: 0.0,
      expenses: 0.0,
      name: this.formGroup.get('name')?.value as string,
      description: this.formGroup.get('description')?.value as string,
      color: this.formGroup.get('color')?.value as string,
      parentId:
        this.availableGroups().find(
          (group) => group.name === this.formGroup.get('parent')?.value
        )?.id || null,
    };

    console.log(transactionGroup);
  }
}
