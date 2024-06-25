import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '@services/auth.service';
import { inject } from '@angular/core';

export const authorizedGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);

  // todo: implement token verification
  if (authService.authenticated || authService.tokensValid) {
    return true;
  }

  void inject(Router).navigate(['']);
  return false;
};
