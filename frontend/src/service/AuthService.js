import { onLogout, onLogin, onRegister } from 'api';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  handleLogin = async (login, password) => {
    try {
      await onLogin(login, password);
    } catch(error) {
      this.onError(error.response.data);
    }
  };

  handleRegister = async (login, password) => {
    try {
      await onRegister(login, password);
    } catch(error) {
      this.onError(error.response.data);
    }
  };

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