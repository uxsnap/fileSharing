import { onLogout, onLogin, onRegister, onResetPassword } from 'api';
import { RES_STATUS } from "nuxxxcomponentlib/dist";

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  handleLogin = async (email, password) => {
    try {
      await onLogin(email, password);
    } catch(error) {
      this.onError(error.response.data);
    }
  };

  handleRegister = async (email, login, password) => {
    try {
      const res = await onRegister(email, login, password);
      console.log(res);
      if (res.status === RES_STATUS.OK) {
        return res.data.message;
      }
    } catch(error) {
      this.onError(error.response.data);
    }
  };

  handleResetPassword = async (email) => {
    try {
      const res = await onResetPassword(email);
      console.log(res);
      if (res.status === RES_STATUS.OK) {
        return res.data.message;
      }
    } catch (error) {
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