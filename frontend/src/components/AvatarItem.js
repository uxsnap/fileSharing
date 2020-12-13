import React from 'react';
import { Icon, Avatar, RoundedInitials } from './index';

export default ({ id, img, name, icon, onIconClick, checked }) => (
	<div className="avatar-item">
		<div className="avatar-item__img">
			{img ? <Avatar data={img} /> : <RoundedInitials name={name} />}
		</div>
		<div className="avatar-item__name">
			{name}
		</div>
		{icon && <div className={`avatar-item__icon ${!checked && 'active' }`}>
				<Icon 
					iconType={!checked ? icon : checked} 
					onClick={(event) => {
						!checked && onIconClick(id)
					}}
				/>
			</div>
		}
	</div>
);