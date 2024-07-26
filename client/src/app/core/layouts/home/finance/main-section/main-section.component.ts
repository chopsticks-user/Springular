import {Component} from '@angular/core';
import {FinanceToolsComponent} from '@core/layouts/home/finance/main-section/tool-bar/finance-tools.component';

@Component({
  selector: 'app-layout-home-finance-main-section',
  standalone: true,
  imports: [FinanceToolsComponent],
  templateUrl: './main-section.component.html',
  styleUrl: './main-section.component.css',
})
export class MainSectionComponent {
}
