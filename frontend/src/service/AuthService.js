import { onLogout } from 'api';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  handleLogout = async () => {
    try {
      await onLogout();
      localStorage.removeItem('TOKEN');
      localStorage.removeItem('USER_NAME');
    } catch (error) {
      this.onError(error);
    }
  };
}