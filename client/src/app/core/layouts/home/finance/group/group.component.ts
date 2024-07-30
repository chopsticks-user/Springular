import {Component, computed, inject, input} from '@angular/core';
import {TransactionGroup} from "@shared/domain/types";
import {MatIcon} from "@angular/material/icon";
import {Router} from "@angular/router";

@Component({
  selector: 'app-layout-home-finance-main-section-group',
  standalone: true,
  imports: [
    MatIcon
  ],
  templateUrl: './group.component.html',
  styleUrl: './group.component.css',
  host: {
    '[title]': "group().description"
  },
})
export class GroupComponent {
  private _router = inject(Router);

  public group = input.required<TransactionGroup>();
  public name = computed(() => {
      const path = this.group().path;
      return path.substring(path.lastIndexOf('/') + 1)
    }
  );

  public navigate(): void {
    void this._router.navigateByUrl(`${this._router.url}/${this.name()}`);
  }
}
