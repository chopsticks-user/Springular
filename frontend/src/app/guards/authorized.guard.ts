import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '@services/auth.service';
import { inject } from '@angular/core';
import { lastValueFrom, map } from 'rxjs';

export const authorizedGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);

  // todo: implement token verification
  if (authService.authenticated || authService.tokensValid) {
    return true;
  }

  // return lastValueFrom(
  //   authService.authenticated$.pipe(
  //     map((authenticated) => {
  //       if (authenticated) {
  //         return true;
  //       }
  //       void inject(Router).navigate(['']);
  //       return false;
  //     })
  //   )
  // );

  void inject(Router).navigate(['']);
  return false;
};
