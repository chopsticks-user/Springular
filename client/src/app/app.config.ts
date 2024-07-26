import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {authHeaderInterceptor} from '@core/providers/auth-header.interceptor';
import {jsonResponseInterceptor} from '@core/providers/json-response.interceptor';
import {apiRouteInterceptor} from '@core/providers/api-route.interceptor';
import {refreshTokenInterceptor} from '@core/providers/refresh-token.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(
      withInterceptors([
        jsonResponseInterceptor,
        apiRouteInterceptor,
        authHeaderInterceptor,
        refreshTokenInterceptor,
      ])
    ), provideAnimationsAsync(),
  ],
};
