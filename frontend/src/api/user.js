import authorized from './authorized';
import axios from 'axios';

// Cancel token object
const CancelToken = axios.CancelToken;

export const getUserData = (userName) => {
	return authorized('get', `/user/${userName}`);
};

export const getUserFiles = (userName) => {
	return authorized('get', `/file/${userName}`);
};

export const uploadAvatar = (userName, file) => {
	const bodyFormData = new FormData();
	bodyFormData.append('avatar', file);
	const token = localStorage.getItem('TOKEN');
	return axios({
		method: 'post',
		url: process.env.REACT_APP_BASE_URL + `/user/${userName}/avatar`,
		data: bodyFormData,
		headers: { 
			'Authorization': `Bearer ${token}`, 
			'Content-Type': 'multipart/form-data' 
		}
	});
};

export const getUserAvatar = (userName) => {
	return authorized('get', `/user/${userName}/avatar`);
};

let getUsersCancel = { cancel: undefined };
export const getUsers = (userName) => {
	return authorized('get', `/user/all`, { userName }, getUsersCancel);
};