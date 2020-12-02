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
	minLength,
	checked,
	checkedIcon
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
	  				<Component {...item} checked={checked && checked.includes(item.id) ? checkedIcon : undefined}/>
	    		)) : Stub ? <Stub /> : ''}
	    	</div>
    	}
		</div>
  );
};