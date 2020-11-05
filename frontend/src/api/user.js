import authorized from './authorized';

export const getUserData = (userName) => {
	return authorized('get', `/user/${userName}`);
};

export const getUserFiles = (userName) => {
	return authorized('get', `/file/${userName}`);
};