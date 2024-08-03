import {Component, computed, inject, input} from '@angular/core';
import {TransactionGroup} from "@shared/domain/types";
import {Router} from "@angular/router";
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-home-finance-main-section-group-view',
  standalone: true,
  imports: [
    IconComponent
  ],
  templateUrl: './group-view.component.html',
  styleUrl: './group-view.component.css',
  host: {
    '[title]': "group().description"
  },
})
export class GroupViewComponent {
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
