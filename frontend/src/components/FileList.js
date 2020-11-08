import React from 'react';
import { File, Button } from './index';

export default ({ items, stub, onDelete, onEdit, addFile }) => (
	<div className="file-list">
		{items && items.length
			? items.map((item) => (
				<File 
					filename={item.name}
					onDelete={() => onDelete(item.id)}
					onEdit={(fileName) => onEdit(item.id, fileName)}
				/>
			)) : stub ? stub : ''
		}
	</div>	
)