import React from 'react';
import { Icon } from './index';

export default ({ src, onClick }) => {
	return (
		<>
			{src 
				? <img src={src} alt="ME" onClick={onClick} />
      	: <Icon iconType="person" onClick={onClick} />
      }
		</>
	)
};