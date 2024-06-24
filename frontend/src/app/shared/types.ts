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

export interface JwtToken {
  token: string;
  expiresAt: string;
}

export interface JwtTokenPack {
  accessToken: JwtToken | null;
  refreshToken: JwtToken | null;
}
