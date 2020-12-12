export default (data) => {
	return data && data.avatar ? `${process.env.REACT_APP_BASE_URL}/${data.avatar}` : '';
};