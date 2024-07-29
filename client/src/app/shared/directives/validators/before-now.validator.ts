import {AbstractControl, ValidationErrors} from "@angular/forms";

export function beforeNowValidator() {
  return (control: AbstractControl<string | Date>): ValidationErrors | null => {
    return new Date(control.value) > new Date()
      ? {beforeNow: {value: control.value}}
      : null;
  }
}
