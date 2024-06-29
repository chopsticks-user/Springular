import { HttpInterceptorFn } from '@angular/common/http';

export const jsonResponseInterceptor: HttpInterceptorFn = (req, next) => {
  return next(
    req.clone({
      responseType: 'json',
    })
  );
};
