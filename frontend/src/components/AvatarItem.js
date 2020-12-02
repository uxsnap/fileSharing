import React from 'react';
import { Icon, Avatar } from './index';

export default ({ id, img, name, icon, onClick, onIconClick, checked }) => (
	<div className="avatar-item" onClick={() => !checked && onClick(id)}>
		<div className="avatar-item__img">
			<Avatar data={img} />
		</div>
		<div className="avatar-item__name">
			{name}
		</div>
		<div className={`avatar-item__icon ${!checked && 'active' }`}>
			<Icon iconType={!checked ? icon : checked} onClick={!checked && onIconClick}/>
		</div>
	</div>
);