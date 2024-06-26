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

export interface CalendarEvent {
  id: number;
  start: Date;
  durationMinutes: number;
  repeat: 'daily' | 'weekly' | 'monthly' | 'yearly' | 'custom' | 'none';
  repeatEvery?: { value: number; unit: 'day' | 'week' | 'month' | 'year' };
  color: string;
}
