import {Component, inject, input, OnInit, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {FormControlErrorDictionary, TransactionGroup} from '@shared/domain/types';
import {ModalComponent} from "@core/layouts/modal/modal.component";
import {GroupComponent} from "@core/layouts/form/group/group.component";
import {FinanceService} from "@features/home/finance/finance.service";
import {FieldComponent} from "@core/layouts/form/field/field.component";
import {directoryValidator} from "@shared/directives/validators/directory.validator";

@Component({
  selector: 'app-layout-home-finance-group-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatIconModule,
    FieldComponent,
    ModalComponent,
    GroupComponent,
    FieldComponent,
  ],
  templateUrl: './group-editor.component.html',
  styleUrl: './group-editor.component.css',
})
export class GroupEditorComponent implements OnInit {
  private _financeService = inject(FinanceService);

  public group = input<TransactionGroup>();

  public availableGroups$ = this._financeService.groups$;
  public readonly errorDictionaries: FormControlErrorDictionary[] = [
    {
      name: 'name',
      entries: [
        {type: 'required', message: 'Name is required'},
        {type: 'pattern', message: 'Name must only include a-z, A-Z, 0-9 and - or _'},
        {type: 'maxLength', message: 'Name must be 20 characters at most'},
      ],
    },
    {
      name: 'description',
      entries: [
        {type: 'maxLength', message: 'Description must be 100 characters at most'},
      ],
    },
    {
      name: 'color',
      entries: [
        {type: 'required', message: 'Color is required'},
      ],
    },
    {
      name: 'directory',
      entries: [
        {type: 'directory', message: 'Invalid directory'},
      ],
    },
  ];
  public formGroup!: FormGroup;

  ngOnInit(): void {
    const transactionGroup: TransactionGroup | undefined = this.group();

    const {name, directory} =
      this.splitPath(transactionGroup?.path || '');
    this.formGroup = new FormGroup({
      name: new FormControl<string>(
        name,
        [
          Validators.required,
          Validators.pattern(/^[a-zA-Z0-9_-]+$/),
          Validators.maxLength(20),
        ],
      ),
      description: new FormControl<string>(
        transactionGroup?.description || '',
        [Validators.maxLength(100)],
      ),
      color: new FormControl<string>(
        transactionGroup?.color || '#7cfc00',
        [Validators.required],
      ),
      directory: new FormControl<string>(
        directory,
        [directoryValidator()],
      ),
    })
    ;
  }

  public submitHandler = (errorMessage: WritableSignal<string>): void => {
    let transactionGroup: TransactionGroup | undefined = this.group();

    const path: string = this.getPath(
      this.formGroup.get('name')?.value as string,
      this.formGroup.get('directory')?.value as string
    );

    if (transactionGroup) {
      transactionGroup = {
        ...transactionGroup,
        path: path,
        description: this.formGroup.get('description')?.value,
        color: this.formGroup.get('color')?.value,
      };
    } else {
      transactionGroup = {
        path: path,
        description: this.formGroup.get('description')?.value as string,
        color: this.formGroup.get('color')?.value as string,
      };
    }

    console.log(transactionGroup);
  }

  private splitPath(path: string): { name: string, directory: string } {
    if (path.trim() === '/' || path.trim() === '') {
      return {name: '', directory: ''};
    }

    const index: number = path.lastIndexOf('/');
    return {
      name: path.substring(0, index).trim(),
      directory: path.substring(index + 1).trim(),
    }
  }

  private getPath(name: string, directory: string): string {
    if (directory === '/') {
      return `/${name.trim()}`;
    }
    return `${directory.trim()}/${name.trim()}`;
  }
}
