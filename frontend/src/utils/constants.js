export const AUTH_TYPES = {
  LOGIN: { type: "login", label: "Login", url: '/login' },
  REGISTRATION: { type: "registration", label: "Registration", url: '/auth/register' },
  FORGOT_PASS: { type: "forgot_pass", label: "Forgot Password", url: '/auth' }
};

export const DEFAULT_TIME_INTERVAL = 3000;


/* This object is HttpStatus mapping. 
	LOADING field is -1 because there is no such status
*/
export const RES_STATUS = {
	OK: 200,
	ERROR: 400,
	LOADING: -1,
};

export const MIN_SEARCH_LENGTH = 2;

export const FRIEND_REQUEST_STATUS = {
  APPROVED: 'approved',
  DECLINED: 'declined',
};