import axios from 'axios';
const { REACT_APP_BASE_URL } = process.env; 

export const onLogin = async (userName, password) => {
	const res = await axios.post(REACT_APP_BASE_URL + '/auth/login', {
		userName,
		password
	});
	if (res.data.token) {
		localStorage.setItem('TOKEN', res.data.token);
		localStorage.setItem('USER_NAME', res.data.username);
	}
	return res;
};

export const onRegister = async (userName, password) => {
 	return await axios.post(REACT_APP_BASE_URL + '/auth/register', {
		userName,
		password
	});
};

export const onLogout = () => {
	return axios.post(REACT_APP_BASE_URL + `/auth/logout`);
};
