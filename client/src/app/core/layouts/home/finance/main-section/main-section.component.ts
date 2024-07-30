import {Component, input} from '@angular/core';
import {FinanceToolsComponent} from '@core/layouts/home/finance/main-section/tool-bar/finance-tools.component';
import {TransactionGroup} from "@shared/domain/types";
import {GroupExplorerComponent} from "@core/layouts/home/finance/main-section/group-explorer/group-explorer.component";
import {GroupSummaryComponent} from "@core/layouts/home/finance/main-section/group-summary/group-summary.component";

@Component({
  selector: 'app-layout-home-finance-main-section',
  standalone: true,
  imports: [FinanceToolsComponent, GroupExplorerComponent, GroupSummaryComponent],
  templateUrl: './main-section.component.html',
  styleUrl: './main-section.component.css',
})
export class MainSectionComponent {
  public rootGroup = input.required<TransactionGroup | null>();
  public childrenGroups = input.required<TransactionGroup[]>();
}
