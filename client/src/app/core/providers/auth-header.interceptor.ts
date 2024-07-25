import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from '@shared/services/auth.service';
import {BYPASS_AUTH_HEADER} from '@shared/domain/constants';

export const authHeaderInterceptor: HttpInterceptorFn = (req, next) => {
  if (req.context.get(BYPASS_AUTH_HEADER)) {
    return next(req);
  }

  // todo: something is wrong here
  return next(
    req.clone({
      headers: req.headers.set(
        'Authorization',
        `Bearer ${inject(AuthService).accessToken?.token}`
      ),
    })
  );
};
