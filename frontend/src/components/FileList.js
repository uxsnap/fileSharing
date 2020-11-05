import React from 'react';
import { File, Button } from './index';

export default ({ items, stub, onDelete, onEdit, addFile }) => (
	<div className="file-list">
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