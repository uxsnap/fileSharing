import axiosConfig from './axiosConfig';
import axios from 'axios';
const CancelToken = axios.CancelToken;

export default async (method, api, params = {}) => {
	const { cancelObject, ...other } = params;
	try {
		if (cancelObject && cancelObject.cancel) cancelObject.cancel(); 
		const res = await axiosConfig[method](api, {
		 	...other,
		 	cancelToken: cancelObject ? new CancelToken(function executor(c) {
		    cancelObject.cancel = c;
		  }) : undefined
		});
		return res;
	} catch(error) {
		return error.response;
	}
};