import {Routes} from '@angular/router';
import {WelcomeComponent} from '@components/welcome/welcome.component';
import {HomeComponent} from '@components/home/home.component';
import {authorizedGuard} from '@guards/authorized.guard';
import {PageNotFoundComponent} from '@components/page-not-found/page-not-found.component';
import {CalendarComponent} from '@components/home/calendar/calendar.component';
import {FinanceComponent} from '@components/home/finance/finance.component';
import {DashboardComponent} from '@components/home/dashboard/dashboard.component';
import {ContactsComponent} from '@components/home/contacts/contacts.component';
import {ProfileComponent} from '@components/home/profile/profile.component';
import {SettingsComponent} from '@components/home/settings/settings.component';
import {FaqComponent} from '@components/home/faq/faq.component';

export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login', redirectTo: '' },
  { path: 'signup', redirectTo: '' },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authorizedGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'prefix' },
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [authorizedGuard],
      },
      {
        path: 'profile',
        component: ProfileComponent,
        canActivate: [authorizedGuard],
      },
      {
        path: 'calendar',
        component: CalendarComponent,
        canActivate: [authorizedGuard],
      },
      {
        path: 'finance',
        component: FinanceComponent,
        canActivate: [authorizedGuard],
      },
      {
        path: 'contacts',
        component: ContactsComponent,
        canActivate: [authorizedGuard],
      },
      {
        path: 'settings',
        component: SettingsComponent,
        canActivate: [authorizedGuard],
      },
      {
        path: 'faq',
        component: FaqComponent,
        canActivate: [authorizedGuard],
      },
    ],
  },
  { path: '**', component: PageNotFoundComponent },
];
