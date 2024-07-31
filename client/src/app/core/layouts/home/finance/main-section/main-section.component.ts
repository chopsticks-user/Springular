import {Component} from '@angular/core';
import {FinanceToolsComponent} from '@core/layouts/home/finance/main-section/tool-bar/finance-tools.component';
import {GroupSummaryComponent} from "@core/layouts/home/finance/main-section/group-summary/group-summary.component";

@Component({
  selector: 'app-layout-home-finance-main-section',
  standalone: true,
  imports: [FinanceToolsComponent, GroupSummaryComponent],
  templateUrl: './main-section.component.html',
  styleUrl: './main-section.component.css',
})
export class MainSectionComponent {
}
