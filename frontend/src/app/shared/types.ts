export interface LoginInfo {
  email: string;
  password: string;
}

export interface SignupInfo {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  email: string;
  password: string;
}

export interface Token {
  token: string;
  expiresAt: string;
}

export interface TokenPack {
  accessToken: Token | null;
  refreshToken: Token | null;
}
