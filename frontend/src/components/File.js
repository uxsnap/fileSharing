import React from 'react';
import { Icon } from './index';

export default ({ onDelete, filename, onEdit }) => (
	<div class="file">
		<div class="file__file">
			<Icon iconType="files" />
		</div>
		<div class="file__name">{filename}</div>
    <div class="file__icons">
			<div class="file__delete">
				<Icon iconType="close" onClick={onDelete}/>
			</div>
			<div class="file__edit">
				<Icon iconType="person" onClick={onEdit}/>
			</div>
    </div>
	</div>
);
