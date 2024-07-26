import {Component} from '@angular/core';
import {MainSectionComponent} from '@core/layouts/home/finance/main-section/main-section.component';
import {RecentActivitiesComponent} from '@core/layouts/home/finance/recent-activities/recent-activities.component';
import {SummarySectionComponent} from "@core/layouts/home/finance/summary-section/summary-section.component";

@Component({
  selector: 'app-home-finance',
  standalone: true,
  imports: [
    MainSectionComponent,
    RecentActivitiesComponent,
    SummarySectionComponent,
  ],
  templateUrl: './finance.component.html',
  styleUrl: './finance.component.css',
})
export class FinanceComponent {
}
