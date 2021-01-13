import React from 'react';
import { Icon, Avatar, RoundedInitials } from './index';

export const AvatarItem = ({ id, img, text, icon, onIconClick, checked }) => (
	<div className="avatar-item">
		<div className="avatar-item__img">
			{img ? <Avatar data={img} /> : <RoundedInitials name={text} />}
		</div>
		<div className="avatar-item__name">
			{text}
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

export default AvatarItem;