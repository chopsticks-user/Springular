import {Component, input} from '@angular/core';
import {TransactionGroup} from "@shared/domain/types";

@Component({
  selector: 'app-layout-home-finance-main-section-group-summary',
  standalone: true,
  imports: [],
  templateUrl: './group-summary.component.html',
  styleUrl: './group-summary.component.css'
})
export class GroupSummaryComponent {
  public rootGroup = input.required<TransactionGroup | null>();
  public childrenGroups = input.required<TransactionGroup[]>();
}
