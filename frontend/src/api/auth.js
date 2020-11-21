import queryExecute from './queryExecute';

export const onLogin = async (userName, password) => {
	const res = await queryExecute('post', '/auth/login', {
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
 	return await queryExecute('post', '/auth/register', {
		userName,
		password
	});
};

export const onLogout = () => {
	return queryExecute('post', `/auth/logout`);
};
