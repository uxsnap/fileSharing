export default (obj) => {
	const res = [];
	for (const key of Object.keys(obj)) {
		res.push({ label: obj[key] });
	}

	return res;
}