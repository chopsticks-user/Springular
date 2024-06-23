import { Injectable } from '@angular/core';
import { TokenPack } from '../shared/types';

@Injectable({
  providedIn: 'root',
})
export class JwtKeeperService {
  save(tokens: TokenPack | string): void {
    if (typeof tokens === 'string') {
      tokens = JSON.parse(tokens) as TokenPack;
    }

    localStorage.setItem('accessToken', JSON.stringify(tokens.accessToken));
    localStorage.setItem('refreshToken', JSON.stringify(tokens.refreshToken));
  }

  load(): TokenPack {
    return {
      accessToken: JSON.parse(localStorage.getItem('accessToken') as string),
      refreshToken: JSON.parse(localStorage.getItem('refreshToken') as string),
    };
  }
}
