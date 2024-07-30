import {Component, inject, input} from '@angular/core';
import {FinanceToolsComponent} from '@core/layouts/home/finance/main-section/tool-bar/finance-tools.component';
import {TransactionGroup} from "@shared/domain/types";
import {GroupSummaryComponent} from "@core/layouts/home/finance/main-section/group-summary/group-summary.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-layout-home-finance-main-section',
  standalone: true,
  imports: [FinanceToolsComponent, GroupSummaryComponent],
  templateUrl: './main-section.component.html',
  styleUrl: './main-section.component.css',
})
export class MainSectionComponent {
  public rootGroup = input.required<TransactionGroup | null>();
  public childrenGroups = input.required<TransactionGroup[]>();


}
