import {NgClass, NgStyle} from '@angular/common';
import {Component, inject, input} from '@angular/core';
import {Router} from '@angular/router';
import {SidebarItem} from '@shared/domain/types';
import {IconComponent} from "@shared/ui/icon/icon.component";

@Component({
  selector: 'app-layout-home-navigation-bar-section',
  standalone: true,
  imports: [NgStyle, NgClass, IconComponent],
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

    // logout
    if (!item.url?.startsWith('/home')) {
      return false;
    }

    return this._router.url.startsWith(item.url!);
  }
}
