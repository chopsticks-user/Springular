import { Component, inject, input, output } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { UserService } from '@services/user.service';
import { TransactionGroup } from '@shared/types';

@Component({
  selector: 'app-home-finance-tools-add-transaction-group',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatButtonModule,
  ],
  templateUrl: './add-transaction-group.component.html',
  styleUrl: './add-transaction-group.component.css',
})
export class AddTransactionGroupComponent {
  private _user = inject(UserService);

  public availableGroups = input<TransactionGroup[]>([]);
  public onCloseModal = output<void>();

  public formGroup = new FormGroup({
    name: new FormControl<string>('', [Validators.required]),
    description: new FormControl<string>('', []),
    color: new FormControl<string>('#7cfc00', [Validators.required]),
  });

  public submit(): void {
    if (!this.formGroup.valid) {
      return;
    }

    this._user.userInfo.subscribe((user) => {
      const transactionGroup: TransactionGroup = {
        revenues: 0.0,
        expenses: 0.0,
        name: this.formGroup.get('name')?.value as string,
        description: this.formGroup.get('description')?.value as string,
        color: this.formGroup.get('color')?.value as string,
        user: user,
      };

      console.log(transactionGroup);
    });
  }
}
