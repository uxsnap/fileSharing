import axiosConfig from './axiosConfig';
import axios from 'axios';
const CancelToken = axios.CancelToken;

export default async (method, api, params = {}) => {
	const token = localStorage.getItem('TOKEN');
	const { cancelObject, ...other } = params;
	console.log(other);
	try {
		if (cancelObject && cancelObject.cancel) cancelObject.cancel(); 
		const res = await axiosConfig[method](api, {
		 	...other,
		 	cancelToken: cancelObject ? new CancelToken(function executor(c) {
		    cancelObject.cancel = c;
		  }) : undefined,
			headers: { "Authorization": "Bearer " + token }
		});
		return res;
	} catch(error) {
		return error.response;
	}
};