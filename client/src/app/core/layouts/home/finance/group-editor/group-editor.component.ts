import {Component, inject, input, OnInit, WritableSignal} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators,} from '@angular/forms';
import {FormControlErrorDictionary, TransactionGroup} from '@shared/domain/types';
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";
import {GroupComponent} from "@core/layouts/form/group/group.component";
import {FinanceService} from "@features/home/finance/finance.service";
import {FieldComponent} from "@core/layouts/form/field/field.component";
import {AsyncPipe} from "@angular/common";
import {toSignal} from "@angular/core/rxjs-interop";
import {directoryValidator} from "@shared/directives/validators/directory.validator";

@Component({
  selector: 'app-layout-home-finance-group-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FieldComponent,
    ModalComponent,
    GroupComponent,
    FieldComponent,
    AsyncPipe,
  ],
  templateUrl: './group-editor.component.html',
  styleUrl: './group-editor.component.css',
})
export class GroupEditorComponent implements OnInit {
  private _financeService = inject(FinanceService);

  public path$ = toSignal(this._financeService.path$);
  public group = input<TransactionGroup>();
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
      name: 'directory',
      entries: [
        {type: 'directory', message: 'Invalid directory'},
      ],
    },
  ];
  public formGroup!: FormGroup;

  ngOnInit():void {
    const transactionGroup: TransactionGroup | undefined = this.group();
    const {name, directory} = this.splitPath(transactionGroup?.path || '/');

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
      directory: new FormControl<string>(
        transactionGroup ? directory : (this.path$() || '/'),
        [directoryValidator()],
      ),
    });
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
      };
    } else {
      transactionGroup = {
        path: path,
        description: this.formGroup.get('description')?.value as string,
      };
    }

    console.log(transactionGroup);
  }

  private splitPath(path: string): { name: string, directory: string } {
    if (path === '/' || path === '') {
      return {name: '', directory: '/'};
    }

    const index: number = path.lastIndexOf('/');
    const parentPath: string = path.substring(0, index);
    return {
      name: path.substring(index + 1),
      directory: parentPath === '' ? '/' : parentPath,
    }
  }

  private getPath(name: string, directory: string): string {
    if (directory === '/') {
      return `/${name.trim()}`;
    }
    return `${directory.trim()}/${name.trim()}`;
  }
}
