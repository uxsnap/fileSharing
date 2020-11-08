import React from 'react';
import { Icon } from './index';

export default ({ error, children, onClose }) => {
	return (
		<div class="error-wrapper">
      {error &&
  			<div class="error-box">
  				<div>
  					<div>Error</div>
  					<div>{error}</div>
  				</div>
  				<Icon iconType="close" onClick={onClose}/>
  			</div>
      }
			{children}
		</div>
	);
};