import React from 'react';
import { Input } from './index';

export default ({ 
	value, 
	onChange, 
	rightIcon, 
	placeholder, 
	items, 
	Component, 
	Stub,
	componentProps,
	minLength
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
    	{items && value.length > minLength &&
	    	<div className="input-select__dropdown">
	    		{items.length ? items.map((item) => (
	  				<Component {...item} />
	    		)) : Stub ? <Stub /> : ''}
	    	</div>
    	}
		</div>
  );
};