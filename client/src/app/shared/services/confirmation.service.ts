import {Injectable, signal} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfirmationService {
  private _open = signal(false);
  private _question = signal('');
  private _accept =
    signal(() => {
    });
  private _decline =
    signal(() => {
    });

  public open = this._open.asReadonly();
  public question = this._question.asReadonly();
  public accept = this._accept.asReadonly();
  public decline = this._decline.asReadonly();

  public show(
    question: string,
    accept: () => void,
    decline: () => void
  ): void {
    this._question.set(question);
    this._accept.set(accept);
    this._decline.set(decline);
    this._open.set(true);
  }

  public close() {
    this._open.set(false);
  }
}
