import axiosConfig from './axiosConfig';
import axios from 'axios';
const CancelToken = axios.CancelToken;

export default async (method, api, params = {}) => {
	const token = localStorage.getItem('TOKEN');
	const { cancelObject, headers, ...other } = params;
	other.headers = undefined;
	try {
		if (cancelObject && cancelObject.cancel) cancelObject.cancel(); 
		return await axiosConfig[method](api, {
		 	...other,
		 	cancelToken: cancelObject ? new CancelToken(function executor(c) {
		    cancelObject.cancel = c;
		  }) : undefined,
			headers: { "Authorization": "Bearer " + token, ...headers }
		});
	} catch(error) {
		return error.response;
	}
};