import { HttpContextToken, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '@services/auth.service';

export const BYPASS_AUTH_HEADER = new HttpContextToken<boolean>(() => false);

export const authHeaderInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.context.get(BYPASS_AUTH_HEADER) === true) {
    return next(req);
  }

  // todo: something is wrong here
  return next(
    req.clone({
      headers: req.headers.append(
        'Authorization',
        `Bearer ${inject(AuthService).accessToken}`
      ),
    })
  );
};
