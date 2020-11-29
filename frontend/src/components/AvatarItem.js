import React from 'react';
import { Icon, Avatar } from './index';

export default ({ id, img, name, icon, onClick, onIconClick }) => (
	<div class="avatar-item" onClick={() => onClick(id)}>
		<div class="avatar-item__img">
			<Avatar data={img} />
		</div>
		<div class="avatar-item__name">
			{name}
		</div>
		<div class="avatar-item__icon">
			<Icon iconType={icon} onClick={onIconClick}/>
		</div>
	</div>
);