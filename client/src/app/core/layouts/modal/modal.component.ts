import {Component, effect, ElementRef, input, output, ViewChild} from '@angular/core';
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-layout-modal',
  standalone: true,
  imports: [
    MatIcon
  ],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.css'
})
export class ModalComponent {
  @ViewChild('modal')
  private _modalRef!: ElementRef<HTMLDialogElement>;

  public modalShouldOpen = input.required<boolean>();
  public modalShouldClose = output<void>();

  constructor() {
    effect(() => {
      if (!this._modalRef) {
        return;
      }

      if (this.modalShouldOpen()) {
        this._modalRef.nativeElement.showModal();
      } else {
        this._modalRef.nativeElement.close();
      }
    });
  }
}
