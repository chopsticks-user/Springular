import {
  HttpClient,
  HttpErrorResponse,
  HttpEventType,
  HttpInterceptorFn,
} from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '@services/auth.service';
import { BYPASS_AUTH_HEADER, REFRESH_TOKEN_REQUESTED } from '@shared/constants';
import { catchError, map, switchMap, tap, throwError } from 'rxjs';

export const refreshTokenInterceptor: HttpInterceptorFn = (req, next) => {
  // todo: move to a service
  if (
    req.context.get(BYPASS_AUTH_HEADER) === true ||
    req.context.get(REFRESH_TOKEN_REQUESTED) === true
  ) {
    return next(req);
  }

  const authService = inject(AuthService);
  const http = inject(HttpClient);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      // ExpiredJwtException in springular.exception.GlobalExceptionHandler.java
      if (error.status === 401) {
        const response$ = authService.refresh();

        if (!response$) {
          return throwError(() => error);
        }

        return response$.pipe(
          switchMap(() => {
            return http.request(
              req.clone({
                headers: req.headers.set(
                  'Authorization',
                  `Bearer ${authService.accessToken?.token}`
                ),
                context: req.context.set(REFRESH_TOKEN_REQUESTED, true),
              })
            );
          }),
          catchError((refreshError: HttpErrorResponse) => {
            return throwError(() => refreshError);
          })
        );
      }

      return throwError(() => error);
    })
  );
};
