import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { inject } from '@angular/core';

export const authorizedGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);

  if (loginService.authenticated) {
    return true;
  }

  void inject(Router).navigate(['']);
  return false;
};
