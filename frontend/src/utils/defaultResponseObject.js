import { RES_STATUS } from './index';

export default () => ({
	status: RES_STATUS.LOADING,
	message: '',
	error: undefined,
	data: []  
});