import { HttpContextToken } from '@angular/common/http';

export const BYPASS_AUTH_HEADER = new HttpContextToken<boolean>(() => false);

export const REFRESH_TOKEN_REQUESTED = new HttpContextToken<boolean>(
  () => false
);
