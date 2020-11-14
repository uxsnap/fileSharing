export default (data) => {
	return process.env.REACT_APP_BASE_URL + data.avatar;
};