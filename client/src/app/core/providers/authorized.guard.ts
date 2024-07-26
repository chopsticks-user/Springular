import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from '@shared/services/auth.service';
import {inject} from '@angular/core';
import {catchError, map, of, switchMap} from 'rxjs';

export const authorizedGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (state.url === '/') {
    return router.parseUrl('/home');
  }

  return authService.authenticated$.pipe(
    switchMap((authenticated) => {
      if (authenticated) {
        return of(true);
      }

      const verifyResponse$ = authService.verify();
      if (!verifyResponse$) {
        void router.navigateByUrl('/auth/login');
        return of(false);
      }

      return verifyResponse$.pipe(
        map(() => true),
        catchError(() => {
          void router.navigateByUrl('/auth/login');
          return of(false);
        })
      );
    })
  );
};
