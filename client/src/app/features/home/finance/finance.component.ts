import {Component, inject} from '@angular/core';
import {MainSectionComponent} from '@core/layouts/home/finance/main-section/main-section.component';
import {RecentActivitiesComponent} from '@core/layouts/home/finance/recent-activities/recent-activities.component';
import {SummarySectionComponent} from "@core/layouts/home/finance/summary-section/summary-section.component";
import {GroupEditorComponent} from "@core/layouts/home/finance/group-editor/group-editor.component";
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";
import {FinanceService} from "@features/home/finance/finance.service";
import {Router} from "@angular/router";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-home-finance',
  standalone: true,
  imports: [
    MainSectionComponent,
    RecentActivitiesComponent,
    SummarySectionComponent,
    GroupEditorComponent,
    ModalComponent,
    AsyncPipe,
  ],
  templateUrl: './finance.component.html',
  styleUrl: './finance.component.css',
})
export class FinanceComponent {
  private _financeService = inject(FinanceService);
  private _router = inject(Router);

  public rootGroup$ = this._financeService.rootGroup$;
  public childrenGroups$ = this._financeService.childrenGroups$;

  constructor() {
    this._financeService.path$.pipe(takeUntilDestroyed()).subscribe(
      path => console.log(path),
    );
    this.rootGroup$.pipe(takeUntilDestroyed()).subscribe(
      rootGroup => !rootGroup && void this._router.navigateByUrl('/home/finance')
    );
  }
}
