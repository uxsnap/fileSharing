import axios from 'axios';
const { REACT_APP_BASE_URL } = process.env; 

export const onLogin = async (email, password) => {
	const res = await axios.post(REACT_APP_BASE_URL + '/auth/login', {
		email,
		password
	});
	if (res.data.token) {
		localStorage.setItem('TOKEN', res.data.token);
		localStorage.setItem('EMAIL', res.data.email);
	}
	return res;
};

export const onRegister = async (email, userName, password) => {
 	return await axios.post(REACT_APP_BASE_URL + '/auth/register', {
		email,
		userName,
		password
	});
};

export const onResetPassword = async (email) => {
	return await axios.post(REACT_APP_BASE_URL + '/auth/resetPassword', {
		email
	});
};

export const onLogout = () => {
	return axios.post(REACT_APP_BASE_URL + `/auth/logout`);
};
