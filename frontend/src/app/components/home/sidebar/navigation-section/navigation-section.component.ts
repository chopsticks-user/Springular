import { NgStyle } from '@angular/common';
import { Component, inject, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { SidebarItem } from '@shared/types';

@Component({
  selector: 'app-home-sidebar-navigation-section',
  standalone: true,
  imports: [MatIconModule, NgStyle],
  templateUrl: './navigation-section.component.html',
  styleUrl: './navigation-section.component.css',
})
export class NavigationSectionComponent {
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

        this._router.navigateByUrl(item.url!);
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
