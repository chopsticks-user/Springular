import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '@services/auth.service';
import { inject } from '@angular/core';
import { lastValueFrom, map } from 'rxjs';

export const authorizedGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);

  if (authService.authenticated) {
    return true;
  }

  void inject(Router).navigate(['']);
  return false;
};
