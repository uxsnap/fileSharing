import React from 'react';
import { Input } from './index';

export default ({ 
	value, 
	onChange, 
	rightIcon, 
	placeholder, 
	items, 
	Component, 
	componentProps 
}) => {
  return (
		<div className="input-select">
			<div className="input-select__input">
				<Input 
	        value={value} 
	        onChange={onChange}
	        placeholder={placeholder}
	        rightIcon={rightIcon}
	      />
    	</div>
    	<div className="input-select__dropdown">
    		{items.length && items.map((item) => (
  				<Component {...item} />
    		))}
    	</div>
		</div>
  );
};