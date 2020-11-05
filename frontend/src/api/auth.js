import queryExecute from './queryExecute';

export const onLogin = async (username, password) => {
	const res = await queryExecute('post', '/auth/login', {
		username,
		password
	});
	console.log(res);
	if (res.data.token) {
		localStorage.setItem('TOKEN', res.data.token);
		localStorage.setItem('USER_NAME', res.data.username);
	}
	return res;
};

export const onRegister = async (username, password) => {
 	return await queryExecute('post', '/auth/register', {
		username,
		password
	});
};
