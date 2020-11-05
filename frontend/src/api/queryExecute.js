import axiosConfig from './axiosConfig';


export default async (method, api, params = {}) => {
	try {
	 const res = await axiosConfig[method](api, params);
	 return res;
	} catch(error) {
		return error.response;
	}
};