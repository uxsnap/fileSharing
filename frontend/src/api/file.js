import axios from 'axios';

export const uploadFile = async (userName, file) => {
	const bodyFormData = new FormData();
	bodyFormData.append('file', file);
	const token = localStorage.getItem('TOKEN');
	return axios({
		method: 'post',
		url: process.env.REACT_APP_BASE_URL + `/file/${userName}`,
		data: bodyFormData,
		headers: { 
			'Authorization': `Bearer ${token}`, 
			'Content-Type': 'multipart/form-data' 
		}

	});
}