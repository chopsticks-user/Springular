import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ConfirmationService {
  private $open = new BehaviorSubject(false);
  private $question = new BehaviorSubject('');
  private $accept =
    new BehaviorSubject(() => {
    });
  private $decline =
    new BehaviorSubject(() => {
    });


  public open$ = this.$open.asObservable();
  public question$ = this.$question.asObservable();
  public accept$ = this.$accept.asObservable();
  public decline$ = this.$decline.asObservable();

  public show(
    question: string,
    accept: () => void,
    decline: () => void
  ): void {
    this.$question.next(question);
    this.$accept.next(accept);
    this.$decline.next(decline);
    this.$open.next(true);
  }

  public close() {
    this.$open.next(false);
  }
}
