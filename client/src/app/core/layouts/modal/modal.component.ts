import {Component, effect, ElementRef, input, output, viewChild} from '@angular/core';
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
  private _modalRef = viewChild.required<ElementRef>('modal');

  public modalShouldOpen = input.required<boolean>();
  public modalShouldClose = output<void>();

  constructor() {
    effect(() => {
      if (!this._modalRef()) {
        return;
      }

      if (this.modalShouldOpen()) {
        this._modalRef().nativeElement.showModal();
      } else {
        this._modalRef().nativeElement.close();
      }
    });
  }

  ngAfterViewInit() {

  }
}
