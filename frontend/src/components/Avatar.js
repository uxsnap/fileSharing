import React from 'react';
import { Icon } from './index';

export default ({ data, onClick }) => {
	return (
		<>
			{data 
				? <img src={data} alt="ME" onClick={onClick} />
      	: <Icon iconType="person" onClick={onClick} />
      }
		</>
	)
};