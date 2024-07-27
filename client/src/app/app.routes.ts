import {Routes} from '@angular/router';
import {HomeComponent} from "@features/home/home.component";
import {authorizedGuard} from "@core/providers/authorized.guard";
import {DashboardComponent} from "@features/home/dashboard/dashboard.component";
import {ProfileComponent} from "@features/home/settings/profile/profile.component";
import {CalendarComponent} from "@features/home/calendar/calendar.component";
import {FinanceComponent} from "@features/home/finance/finance.component";
import {ContactsComponent} from "@features/home/contacts/contacts.component";
import {SettingsComponent} from "@features/home/settings/settings.component";
import {FaqComponent} from "@features/home/faq/faq.component";
import {PageNotFoundComponent} from "@features/page-not-found/page-not-found.component";
import {LoginComponent} from "@features/auth/login/login.component";
import {SignupComponent} from "@features/auth/signup/signup.component";
import {ThemesComponent} from "@features/home/settings/themes/themes.component";
import {LanguagesComponent} from "@features/home/settings/languages/languages.component";
import {AuthComponent} from "@features/auth/auth.component";
import {ResetPasswordComponent} from "@features/auth/reset-password/reset-password.component";
import {WelcomeComponent} from "@features/welcome/welcome.component";

export const routes: Routes = [
  {
    path: '',
    component: WelcomeComponent,
  },
  {
    path: 'welcome',
    redirectTo: '/',
  },
  {
    path: 'auth',
    component: AuthComponent,
    children: [
      {
        path: '',
        redirectTo: 'login',
        pathMatch: 'prefix',
      },
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'signup',
        component: SignupComponent,
      }, {
        path: 'reset-password',
        component: ResetPasswordComponent,
      },
    ],
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authorizedGuard],
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'prefix',
      },
      {
        path: 'dashboard',
        component: DashboardComponent,
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
        children: [
          {
            path: 'profile',
            component: ProfileComponent,
            canActivate: [authorizedGuard],
          },
          {
            path: 'themes',
            component: ThemesComponent,
            canActivate: [authorizedGuard],
          },
          {
            path: 'languages',
            component: LanguagesComponent,
            canActivate: [authorizedGuard],
          },
        ],
      },
      {
        path: 'faq',
        component: FaqComponent,
        canActivate: [authorizedGuard],
      },
    ],
  },
  {path: '**', component: PageNotFoundComponent},
];
