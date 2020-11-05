import React from 'react';
import loader from 'assets/loader.gif';

export default () => {
	return (
		<div className="loader">
			<img src={loader} alt="LOADER" />
		</div>
	);
}