import {AbstractControl, ValidationErrors} from "@angular/forms";

export function divisibleByValidator(divider: number) {
  return (control: AbstractControl<number | null>): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }
    return control.value % divider !== 0
      ? {divisibleBy: {value: control.value}}
      : null;
  }
}
