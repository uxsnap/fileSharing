import React from 'react';
import { File } from './index';

export default ({ items, stub, onDelete, onEdit }) => (
	<div class="file-list">
		{items && items.length
			? items.map((item) => (
				<File 
					filename={item.name}
					onDelete={() => onDelete(item.id)}
					onEdit={() => onEdit(item.id)}
				/>
			)) : stub ? stub : ''
		}
	</div>	
)