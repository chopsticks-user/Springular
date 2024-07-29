import {Component, effect, ElementRef, inject, viewChild} from '@angular/core';
import {ConfirmationService} from "@shared/services/confirmation.service";
import {AsyncPipe} from "@angular/common";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-layout-dialog-confirmation',
  standalone: true,
  imports: [
    AsyncPipe,
    MatIcon
  ],
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent {
  private _dialogRef =
    viewChild<ElementRef>('confirmationDialog');
  private _confirmationService = inject(ConfirmationService);

  public open = this._confirmationService.open;
  public accept = this._confirmationService.accept;
  public decline = this._confirmationService.decline;
  public question = this._confirmationService.question;

  constructor() {
    effect(() => {
      const dialogRef = this._dialogRef();
      if (!dialogRef) {
        return;
      }

      if (this.open()) {
        dialogRef.nativeElement.showModal();
      } else {
        dialogRef.nativeElement.close();
      }
    });
  }

  public runDecline() {
    this.decline()();
    this._confirmationService.close();
  }

  public runAccept() {
    this.accept()();
    this._confirmationService.close();
  }
}
