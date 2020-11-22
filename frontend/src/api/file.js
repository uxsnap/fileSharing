import axios from 'axios';
import authorized from './authorized';

export const uploadFile = async (file) => {
	const bodyFormData = new FormData();
	bodyFormData.append('file', file);
	const token = localStorage.getItem('TOKEN');
	return axios({
		method: 'post',
		url: process.env.REACT_APP_BASE_URL + '/file/',
		data: bodyFormData,
		headers: { 
			'Authorization': `Bearer ${token}`, 
			'Content-Type': 'multipart/form-data' 
		}

	});
};

export const deleteFile = async (fileId) => {
	return authorized('delete', `/file/${fileId}`);
};

export const editFile = async (fileId, fileName) => {
	return authorized('patch', `/file/${fileId}`, { fileName });
};