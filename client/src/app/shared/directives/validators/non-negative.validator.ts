import {AbstractControl, ValidationErrors} from "@angular/forms";

export function nonNegativeValidator() {
  return (control: AbstractControl<number|null>): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }
    return control.value < 0.0
      ? {nonNegative: {value: control.value}}
      : null;
  }
}
