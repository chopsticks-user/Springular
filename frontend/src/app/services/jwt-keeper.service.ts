import { Injectable } from '@angular/core';
import { Token, TokenPack } from '@shared/types';

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
      accessToken: this.accessToken,
      refreshToken: this.refreshToken,
    };
  }

  clear(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  get accessToken(): Token | null {
    return JSON.parse(localStorage.getItem('accessToken') ?? 'null');
  }

  get refreshToken(): Token | null {
    return JSON.parse(localStorage.getItem('refreshToken') ?? 'null');
  }
}
