import {Injectable} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {FormControlErrorDictionary} from "@shared/domain/types";

@Injectable({
  providedIn: 'root'
})
export class FormService {
  public getErrorMessage(
    formGroup: FormGroup,
    dictionaries: FormControlErrorDictionary[]
  ): string {
    if (!formGroup.invalid || dictionaries.length === 0) {
      return '';
    }

    const dictionary = dictionaries.find(
      (dict) =>
        formGroup.get(dict.name)?.invalid
    );

    if (!dictionary) {
      return '';
    }

    const formControl = formGroup.get(dictionary.name);
    return dictionary.entries.find((entry) =>
      formControl?.hasError(entry.type)
    )?.message || '';
  }
}
