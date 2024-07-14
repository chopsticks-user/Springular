import { Component } from '@angular/core';
import { UserSectionComponent } from './user-section/user-section.component';
import { NavigationSectionComponent } from './navigation-section/navigation-section.component';

interface SidebarItem {
  name: string;
  icon: string;
}

interface SidebarSection {
  id: number;
  name?: string;
  items: SidebarItem[];
}

@Component({
  selector: 'app-home-sidebar',
  standalone: true,
  imports: [UserSectionComponent, NavigationSectionComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent {
  public readonly sections: SidebarSection[] = [];
}
