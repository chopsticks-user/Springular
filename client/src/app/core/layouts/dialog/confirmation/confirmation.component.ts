import {Component, ElementRef, inject, viewChild} from '@angular/core';
import {ConfirmationService} from "@shared/services/confirmation.service";
import {AsyncPipe} from "@angular/common";
import {tap} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-dialog-confirmation',
  standalone: true,
  imports: [
    AsyncPipe,
    IconComponent
  ],
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent {
  private _dialogRef =
    viewChild<ElementRef>('confirmationDialog');
  private _confirmationService = inject(ConfirmationService);

  public open$ =
    this._confirmationService.open$.pipe(
      tap(open => {
        const dialogRef = this._dialogRef();

        if (!dialogRef) {
          return;
        }

        if (open) {
          dialogRef.nativeElement.showModal();
        } else {
          dialogRef.nativeElement.close();
        }
      }),
      takeUntilDestroyed(),
    );
  public question$ = this._confirmationService.question$;
  public accept$ =
    this._confirmationService.accept$.pipe(
      takeUntilDestroyed(),
    );
  public decline$ =
    this._confirmationService.decline$.pipe(
      takeUntilDestroyed(),
    );

  public declineHandler() {
    this.decline$.subscribe(decline => decline());
    this._confirmationService.close();
  }

  public acceptHandler() {
    this.accept$.subscribe(accept => accept());
    this._confirmationService.close();
  }
}
