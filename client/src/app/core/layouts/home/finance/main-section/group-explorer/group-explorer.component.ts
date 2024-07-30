import {Component, input} from '@angular/core';
import {AsyncPipe} from "@angular/common";
import {GroupComponent} from "@core/layouts/home/finance/main-section/group-explorer/group/group.component";
import {TransactionGroup} from "@shared/domain/types";

@Component({
  selector: 'app-layout-home-finance-main-section-group-explorer',
  standalone: true,
  imports: [
    AsyncPipe,
    GroupComponent
  ],
  templateUrl: './group-explorer.component.html',
  styleUrl: './group-explorer.component.css'
})
export class GroupExplorerComponent {
  public childrenGroups = input.required<TransactionGroup[]>();
}
