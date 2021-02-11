export const AUTH_TYPES = {
  LOGIN: { type: "login", label: "Login", url: '/login' },
  REGISTRATION: { type: "registration", label: "Registration", url: '/auth/register' },
  FORGOT_PASS: { type: "forgot_pass", label: "Forgot Password", url: '/auth' }
};

export const DEFAULT_TIME_INTERVAL = 3000;

export const MIN_SEARCH_LENGTH = 2;

export const FRIEND_REQUEST_STATUS = {
  APPROVED: 'approved',
  DECLINED: 'declined',
};