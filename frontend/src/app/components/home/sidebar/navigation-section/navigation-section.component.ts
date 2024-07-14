import { Component, inject, input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { SidebarItem } from '@shared/types';

@Component({
  selector: 'app-home-sidebar-navigation-section',
  standalone: true,
  imports: [MatIconModule],
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
        this.navigateTo(item.url!);
        break;
      }
      case 'dropdown': {
        break;
      }
      case 'toggle': {
        break;
      }
    }

    if (item.sideEffects) {
      item.sideEffects();
    }
  }

  private navigateTo(url: string): void {
    void this._router.navigateByUrl(url);
  }
}
