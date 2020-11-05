import queryExecute from './queryExecute';

export default (...params) => {
	const token = localStorage.getItem('TOKEN');
	return queryExecute(...params, { headers: { "Authorization": `Bearer ${token}` }});
}