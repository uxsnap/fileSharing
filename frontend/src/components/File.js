import React from 'react';
import { Icon } from './index';

export default ({ onDelete, filename, onEdit }) => (
	<div className="file">
		<div className="file__file">
			<Icon iconType="files" />
		</div>
		<div className="file__name">{filename}</div>
    <div className="file__icons">
			<div className="file__delete">
				<Icon iconType="close" onClick={onDelete}/>
			</div>
			<div className="file__edit">
				<Icon iconType="person" onClick={onEdit}/>
			</div>
    </div>
	</div>
);
