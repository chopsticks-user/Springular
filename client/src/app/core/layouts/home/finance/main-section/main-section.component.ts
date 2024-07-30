import {Component, inject} from '@angular/core';
import {FinanceToolsComponent} from '@core/layouts/home/finance/main-section/tool-bar/finance-tools.component';
import {FinanceService} from "@features/home/finance/finance.service";
import {ActivatedRoute, Router} from "@angular/router";
import {map, switchMap, take} from "rxjs";

@Component({
  selector: 'app-layout-home-finance-main-section',
  standalone: true,
  imports: [FinanceToolsComponent],
  templateUrl: './main-section.component.html',
  styleUrl: './main-section.component.css',
})
export class MainSectionComponent {
  private _financeService = inject(FinanceService);
  private _activatedRoute = inject(ActivatedRoute);
  private _router = inject(Router);

  public path$ = this._activatedRoute?.url.pipe(
    map(segments =>
      segments.map(segment => segment.path)
        .reduce((acc, curr) => acc + `/${curr}`, '')
        .padEnd(1, '/')
    )
  );
  public groups$ = this.path$.pipe(
    switchMap(groupPath =>
      this._financeService.getGroupAndDirectChildrenAt(groupPath)
    ),
  );

  constructor() {
    this.groups$.pipe(take(1))
      .subscribe(groups => {
        console.log(groups);
        if (groups.length === 0) {
          this.path$.pipe(take(1))
            .subscribe((path) =>
              void this._router.navigateByUrl(
                this._router.url.substring(0, this._router.url.lastIndexOf(path))
              )
            );
        }
      });
  }
}
