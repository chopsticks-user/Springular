import {AbstractControl, ValidationErrors} from "@angular/forms";

export function divisibleByMinutesValidator(minutes: number) {
  return (control: AbstractControl<string | Date>): ValidationErrors | null => {
    return new Date(control.value).getMinutes() % minutes !== 0
      ? {divisibleByMinutes: {value: control.value}}
      : null;
  }
}
