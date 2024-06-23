export interface Token {
  token: string;
  expiresAt: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
}
