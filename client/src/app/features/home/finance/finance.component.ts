import {Component} from '@angular/core';
import {MainSectionComponent} from '@core/layouts/home/finance/main-section/main-section.component';
import {RecentActivitiesComponent} from '@core/layouts/home/finance/recent-activities/recent-activities.component';
import {SummarySectionComponent} from "@core/layouts/home/finance/summary-section/summary-section.component";
import {GroupEditorComponent} from "@core/layouts/home/finance/group-editor/group-editor.component";
import {ModalComponent} from "@core/layouts/dialog/modal/modal.component";

@Component({
  selector: 'app-home-finance',
  standalone: true,
  imports: [
    MainSectionComponent,
    RecentActivitiesComponent,
    SummarySectionComponent,
    GroupEditorComponent,
    ModalComponent,
  ],
  templateUrl: './finance.component.html',
  styleUrl: './finance.component.css',
})
export class FinanceComponent {
}
