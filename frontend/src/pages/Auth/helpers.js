import { AUTH_TYPES } from "utils";

export const getCurrentPageTypeLabel = (curType) => {
  return curType === AUTH_TYPES.LOGIN.type
    ? AUTH_TYPES.LOGIN.label
    : curType === AUTH_TYPES.REGISTRATION.type
      ? AUTH_TYPES.REGISTRATION.label
      : AUTH_TYPES.FORGOT_PASS.label;
};

export const createPageChangeButtons = (currentPageType) => {
  let firstButton, secondButton;

  if (currentPageType !== AUTH_TYPES.REGISTRATION.type) {
    firstButton = AUTH_TYPES.REGISTRATION;
  } else {
    firstButton = AUTH_TYPES.LOGIN;
  }

  if (currentPageType !== AUTH_TYPES.FORGOT_PASS.type) {
    secondButton = AUTH_TYPES.FORGOT_PASS;
  } else {
    secondButton = AUTH_TYPES.LOGIN;
  }

  return [firstButton, secondButton];
};