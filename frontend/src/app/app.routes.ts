import { Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { HomeComponent } from './components/home/home.component';
import { authorizedGuard } from './guards/authorized.guard';
import { factory } from 'typescript';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';

export const routes: Routes = [
  { path: '', component: WelcomeComponent, canActivate: [] },
  { path: 'home', component: HomeComponent, canActivate: [authorizedGuard] },
  { path: '**', component: PageNotFoundComponent, canActivate: [] },
];
