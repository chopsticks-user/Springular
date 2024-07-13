import { Routes } from '@angular/router';
import { WelcomeComponent } from '@components/welcome/welcome.component';
import { HomeComponent } from '@components/home/home.component';
import { authorizedGuard } from '@guards/authorized.guard';
import { PageNotFoundComponent } from '@components/page-not-found/page-not-found.component';
import { CalendarComponent } from '@components/home/calendar/calendar.component';
import { FinanceComponent } from '@components/home/finance/finance.component';
import { DashboardComponent } from '@components/home/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', component: WelcomeComponent, canActivate: [authorizedGuard] },
  { path: 'login', redirectTo: '' },
  { path: 'signup', redirectTo: '' },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authorizedGuard],
    children: [
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
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [authorizedGuard],
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'prefix' },
    ],
  },
  { path: '**', component: PageNotFoundComponent },
];
