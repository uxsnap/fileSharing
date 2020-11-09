import React from 'react';
import { Icon } from './index';

export default ({ error, children, onClose }) => {
	return (
		<div className="error-wrapper">
      {error &&
  			<div className="error-box">
  				<div>
  					<div>Error</div>
  					<div>{error.message}</div>
  				</div>
  				<Icon iconType="close" onClick={onClose}/>
  			</div>
      }
			{children}
		</div>
	);
};