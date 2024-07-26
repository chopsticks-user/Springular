import {Component, inject} from '@angular/core';
import {UserComponent} from './user/user.component';
import {SectionComponent} from './section/section.component';
import {SidebarSection} from '@shared/domain/types';
import {AuthService} from '@shared/services/auth.service';

@Component({
  selector: 'app-layout-home-navigation-bar',
  standalone: true,
  imports: [UserComponent, SectionComponent],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css',
})
export class NavigationBarComponent {
  private _authService = inject(AuthService);

  public readonly sections: SidebarSection[] = [
    {
      items: [
        {
          name: 'dashboard',
          icon: 'home',
          action: 'navigate',
          url: '/home/dashboard',
        },
      ],
    },
    {
      name: 'tool-bar',
      items: [
        {
          name: 'calendar & events',
          icon: 'edit_calendar',
          action: 'navigate',
          url: '/home/calendar',
        },
        {
          name: 'spending tracker',
          icon: 'account_balance',
          action: 'navigate',
          url: '/home/finance',
        },
        {
          name: 'contacts book',
          icon: 'groups',
          action: 'navigate',
          url: '/home/contacts',
        },
      ],
    },
    {
      name: 'help',
      items: [
        {
          name: 'contact us',
          icon: 'contact_support',
          action: 'navigate',
          url: 'https://github.com/chopsticks-user/Springular/issues',
          externalRedirect: true,
        },
        {
          name: 'request features',
          icon: 'add_task',
          action: 'navigate',
          url: 'https://github.com/chopsticks-user/Springular/issues',
          externalRedirect: true,
        },
        {
          name: 'report bugs',
          icon: 'bug_report',
          action: 'navigate',
          url: 'https://github.com/chopsticks-user/Springular/issues',
          externalRedirect: true,
        },
        {
          name: 'FAQ page',
          icon: 'quiz',
          action: 'navigate',
          url: '/home/faq',
        },
      ],
    },
    {
      name: 'account',
      items: [
        {
          name: 'settings',
          icon: 'settings',
          action: 'navigate',
          url: '/home/settings',
        },
        {
          name: 'sign out',
          icon: 'logout',
          action: 'navigate',
          url: '/',
          sideEffects: () => this._authService.logout(),
        },
      ],
    },
  ];
}
