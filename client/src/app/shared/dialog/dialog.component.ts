import { Component, ElementRef, input, ViewChild } from '@angular/core';

@Component({
  selector: 'app-shared-dialog',
  standalone: true,
  imports: [],
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.css',
})
export class DialogComponent {
  @ViewChild('modal', { static: true })
  private _modalRef!: ElementRef<HTMLDialogElement>;

  public visible = input.required<boolean>();

  // public open(): void {
  //   this._modalRef.nativeElement.showModal();
  // }

  // public close(): void {
  //   this._modalRef.nativeElement.close();
  // }
}
