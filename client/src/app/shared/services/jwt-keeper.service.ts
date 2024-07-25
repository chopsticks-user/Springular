import {Injectable} from '@angular/core';
import {JwtToken, JwtTokenPack} from '@shared/domain/types';

@Injectable({
  providedIn: 'root',
})
export class JwtKeeperService {
  save(tokens: JwtTokenPack | string): void {
    if (typeof tokens === 'string') {
      tokens = JSON.parse(tokens) as JwtTokenPack;
    }

    localStorage.setItem('accessToken', JSON.stringify(tokens.accessToken));
    localStorage.setItem('refreshToken', JSON.stringify(tokens.refreshToken));
  }

  load(): JwtTokenPack {
    return {
      accessToken: this.accessToken,
      refreshToken: this.refreshToken,
    };
  }

  clear(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  get accessTokenValid(): boolean {
    return this.tokenValid(this.accessToken);
  }

  get refreshTokenValid(): boolean {
    return this.tokenValid(this.accessToken);
  }

  get tokensValid(): boolean {
    return this.accessTokenValid && this.refreshTokenValid;
  }

  get accessToken(): JwtToken | null {
    return JSON.parse(localStorage.getItem('accessToken') ?? 'null');
  }

  get refreshToken(): JwtToken | null {
    return JSON.parse(localStorage.getItem('refreshToken') ?? 'null');
  }

  private tokenValid(token: JwtToken | null): boolean {
    return (
      token != null &&
      new Date(Date.parse(token.expiresAt)) > new Date(Date.now())
    );
  }
}
