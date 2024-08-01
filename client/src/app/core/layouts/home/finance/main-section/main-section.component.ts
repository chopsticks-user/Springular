import {Component} from '@angular/core';
import {GroupExplorerComponent} from '@core/layouts/home/finance/main-section/group-explorer/group-explorer.component';
import {FinanceTrendComponent} from "@core/layouts/home/finance/main-section/finance-trend/finance-trend.component";

@Component({
  selector: 'app-layout-home-finance-main-section',
  standalone: true,
  imports: [GroupExplorerComponent, FinanceTrendComponent],
  templateUrl: './main-section.component.html',
  styleUrl: './main-section.component.css',
})
export class MainSectionComponent {
}
