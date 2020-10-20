import { REQUEST_STATUS } from './index';

export default () => ({
	status: REQUEST_STATUS.LOADING,
	message: '',
	error: undefined,
	data: []  
});