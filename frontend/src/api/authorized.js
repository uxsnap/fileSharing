import queryExecute from './queryExecute';

export default (method, url, params, cancelObject) => {
	const token = localStorage.getItem('TOKEN');
	return queryExecute(method, url, { ...params, cancelObject }, { headers: { "Authorization": `Bearer ${token}` }});
}