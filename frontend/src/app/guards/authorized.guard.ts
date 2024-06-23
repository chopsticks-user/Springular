import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { inject } from '@angular/core';

export const authorizedGuard: CanActivateFn = (route, state) => {
  if (!inject(LoginService).authenticated) {
    inject(Router).navigate(['']);
    return false;
  }

  return true;
};
