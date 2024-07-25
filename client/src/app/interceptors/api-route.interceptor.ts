import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {ApiRouteService} from '@services/api-route.service';

export const apiRouteInterceptor: HttpInterceptorFn = (req, next) => {
  return next(
    req.clone({
      url: inject(ApiRouteService).route(req.url),
    })
  );
};
