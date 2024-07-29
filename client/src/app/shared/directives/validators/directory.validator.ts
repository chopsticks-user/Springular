import {AbstractControl, ValidationErrors, Validators} from "@angular/forms";

export function directoryValidator() {
  return (control: AbstractControl<string>): ValidationErrors | null => {
    return Validators.pattern(
      /^(\/([a-zA-Z][a-zA-Z0-9_-]*))+(?<!\/)$/
    )(control) ? {directoryValidator: {value: control.value}} : null;
  }
}
