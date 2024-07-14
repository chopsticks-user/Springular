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

export interface UserInfo {
  firstName: string;
  lastName: string;
  dateOfBirth: string;
  email: string;
}

export interface JwtToken {
  token: string;
  expiresAt: string;
}

export interface JwtTokenPack {
  accessToken: JwtToken | null;
  refreshToken: JwtToken | null;
}

export const calendarEventRepeatOptions = [
  'daily',
  'weekly',
  'monthly',
  'yearly',
  'custom',
  'none',
] as const;

export type CalendarEventRepeat = (typeof calendarEventRepeatOptions)[number];

export const calendarEventRepeatEveryUnits = [
  'days',
  'weeks',
  'months',
  'years',
] as const;

export type CalendarEventRepeatEveryUnit =
  (typeof calendarEventRepeatEveryUnits)[number];

export interface CalendarEventRepeatEvery {
  value: number; // interger
  unit: CalendarEventRepeatEveryUnit;
}

export interface CalendarEvent {
  id?: string;
  // participants: UserInfo[];
  title: string;
  description: string;
  color: string;
  start: Date;
  durationMinutes: number;
  repeat: CalendarEventRepeat;
  repeatEvery?: CalendarEventRepeatEvery;
}

export interface CalendarWeekDay {
  dayOfWeek: string;
  dayOfMonth: number;
}

export type EventEditorTypes = 'add' | 'edit';

export type SidebarItemActionType = 'navigate' | 'toggle' | 'dropdown';

export interface SidebarItem {
  name: string;
  icon: string;
  action: SidebarItemActionType;
  url?: string;
  sideEffects?: () => void;
  externalRedirect?: boolean;
}

export interface SidebarSection {
  name?: string;
  items: SidebarItem[];
}
