import {Component, inject, input, OnInit, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {GroupComponent} from "@core/layouts/form/group/group.component";
import {FinanceService} from "@features/home/finance/finance.service";
import {FormControlErrorDictionary, Transaction} from "@shared/domain/types";
import {divisibleByValidator} from "@shared/directives/validators/divisible-by.validator";
import {nonNegativeValidator} from "@shared/directives/validators/non-negative.validator";
import {beforeNowValidator} from "@shared/directives/validators/before-now.validator";
import {directoryValidator} from "@shared/directives/validators/directory.validator";
import {FieldComponent} from "@core/layouts/form/field/field.component";

@Component({
  selector: 'app-layout-home-finance-transaction-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatIconModule,
    FieldComponent,
    GroupComponent,
    FieldComponent,
  ],
  templateUrl: './transaction-editor.component.html',
  styleUrl: './transaction-editor.component.css',
})
export class TransactionEditorComponent implements OnInit {
  private _financeService = inject(FinanceService);

  public transaction = input<Transaction>();
  public errorDictionaries: FormControlErrorDictionary[] = [
    {
      name: 'revenues',
      entries: [
        {type: 'nonNegative', message: 'Revenues must be non-negative'},
        {type: 'divisibleBy', message: 'Revenues must be divisible by $0.01'},
      ],
    },
    {
      name: 'expenses',
      entries: [
        {type: 'nonNegative', message: 'Expenses must be non-negative'},
        {type: 'divisibleBy', message: 'Expenses must be divisible by $0.01'},
      ],
    },
    {
      name: 'time',
      entries: [
        {type: 'required', message: 'Transaction time is required'},
        {type: 'beforeNow', message: 'Transaction time must be in the past'},
      ],
    },
    {
      name: 'note',
      entries: [
        {type: 'required', message: 'Transaction note is required'},
        {type: 'maxLength', message: 'Transaction note must be at most 100 characters'},
      ],
    },
    {
      name: 'path',
      entries: [
        {type: 'required', message: 'Transaction group path is required'},
        {type: 'directory', message: 'Invalid transaction group path'},
      ],
    },
  ];
  public formGroup!: FormGroup;

  ngOnInit() {
    const _transaction = this.transaction();

    this.formGroup = new FormGroup({
      revenues: new FormControl<number | null>(
        _transaction?.revenues || null,
        [
          nonNegativeValidator(),
          divisibleByValidator(0.01),
        ],
      ),
      expenses: new FormControl<number | null>(
        _transaction?.expenses || null,
        [
          nonNegativeValidator(),
          divisibleByValidator(0.01),
        ],
      ),
      time: new FormControl<string>(
        _transaction?.time.toISOString() || '',
        [
          Validators.required,
          beforeNowValidator(),
        ],
      ),
      note: new FormControl<string>(
        _transaction?.note || '', [
          Validators.required,
          Validators.maxLength(100),
        ],
      ),
      path: new FormControl<string>(
        _transaction?.path || '/',
        [
          Validators.required,
          directoryValidator()
        ],
      ),
    });
  }


  public submitHandler = (errorMessage: WritableSignal<string>): void => {
    let _transaction: Transaction | undefined = this.transaction();

    if (_transaction) {
      _transaction = {
        ..._transaction,
        revenues: this.formGroup.get('revenues')?.value || 0 as number,
        expenses: this.formGroup.get('expenses')?.value || 0 as number,
        time: new Date(this.formGroup.get('time')?.value as string),
        note: this.formGroup.get('note')?.value as string,
        path: this.formGroup.get('path')?.value as string,
      };
    } else {
      _transaction = {
        revenues: this.formGroup.get('revenues')?.value || 0 as number,
        expenses: this.formGroup.get('expenses')?.value || 0 as number,
        time: new Date(this.formGroup.get('time')?.value as string),
        note: this.formGroup.get('note')?.value as string,
        path: this.formGroup.get('path')?.value as string,
      };
    }

    console.log(_transaction);
  }
}
