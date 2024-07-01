import { Routes } from '@angular/router';
import { WelcomeComponent } from '@components/welcome/welcome.component';
import { HomeComponent } from '@components/home/home.component';
import { authorizedGuard } from '@guards/authorized.guard';
import { PageNotFoundComponent } from '@components/page-not-found/page-not-found.component';

export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login', redirectTo: '' },
  { path: 'signup', redirectTo: '' },
  // { path: 'home', component: HomeComponent, canActivate: [authorizedGuard] },
  { path: 'home', component: HomeComponent },
  { path: '**', component: PageNotFoundComponent },
];
