import {Component, effect, ElementRef, input, output, ViewChild} from '@angular/core';

@Component({
  selector: 'app-layout-dialog-modal',
  standalone: true,
  imports: [],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.css'
})
export class ModalComponent {
  @ViewChild('modal', {static: true})
  private _modalRef!: ElementRef<HTMLDialogElement>;

  public modalShouldOpen = input.required<boolean>();
  public modalShouldClose = output<void>();

  constructor() {
    effect(() => {
      if (this.modalShouldOpen()) {
        this._modalRef.nativeElement.showModal();
      } else {
        this._modalRef.nativeElement.close();
      }
    });
  }
}