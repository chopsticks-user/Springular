import {NgClass, NgStyle} from '@angular/common';
import {Component, inject, input} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {Router} from '@angular/router';
import {SidebarItem} from '@shared/domain/types';
import {MatSuffix} from "@angular/material/form-field";

@Component({
  selector: 'app-home-navigation-bar-section',
  standalone: true,
  imports: [MatIconModule, NgStyle, MatSuffix, NgClass],
  templateUrl: './section.component.html',
  styleUrl: './section.component.css',
})
export class SectionComponent {
  private _router = inject(Router);

  public name = input<string>();
  public items = input<SidebarItem[]>([]);

  public onItemClicked(item: SidebarItem): void {
    switch (item.action) {
      case 'navigate': {
        if (item.externalRedirect) {
          window.open(item.url, '_blank');
          break;
        }

        void this._router.navigateByUrl(item.url!);
        break;
      }
      case 'dropdown': {
        break;
      }
      case 'toggle': {
        break;
      }
    }

    item.sideEffects && item.sideEffects();
  }

  public isSelectedItem(item: SidebarItem): boolean {
    if (item.action !== 'navigate') {
      return false;
    }

    return item.url! === this._router.url;
  }
}
