import React from 'react';
import {checkClass} from "../utils";
import { Icon } from './index';

export default ({ 
	id, 
	label, 
	type, 
	value, 
	onChange, 
	className, 
	placeholder,
	rightIcon,
  onRightIconClick
}) => {
  return (
    <div className={checkClass("iinput", className)}>
      <label className="iinput__label" htmlFor={id}>{label}</label>
      <input 
      	className="iinput__input" 
      	type={type} 
      	onChange={(e) => onChange(e.target.value)} 
      	value={value}
      	placeholder={placeholder}
				autoComplete='off'
    	/>
      {rightIcon &&  
        <Icon 
          iconType={rightIcon}
          onClick={onRightIconClick} 
      />}
    </div>
  );
}
