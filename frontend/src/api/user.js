import queryExecute from './queryExecute';
import axios from 'axios';

// Cancel token object
const CancelToken = axios.CancelToken;

export const getUserData = () => {
	return queryExecute('get', '/user');
};

export const getUserFiles = (userName) => {
	return queryExecute('get', '/file');
};

export const uploadAvatar = (file) => {
	const bodyFormData = new FormData();
	bodyFormData.append('avatar', file);
	const token = localStorage.getItem('TOKEN');
	return axios({
		method: 'post',
		url: process.env.REACT_APP_BASE_URL + `/user/avatar`,
		data: bodyFormData,
		headers: { 
			'Authorization': `Bearer ${token}`, 
			'Content-Type': 'multipart/form-data' 
		}
	});
};

export const getUserAvatar = () => {
	return queryExecute('get', '/user/avatar');
};

let getUsersCancel = { cancel: undefined };
export const getUsers = (userName) => {
	return queryExecute('get', `/user/all?userName=${userName}`, {}, getUsersCancel);
};

