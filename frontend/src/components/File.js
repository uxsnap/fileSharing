import React, { useState } from 'react';
import { Icon, Input } from './index';

export default ({ onDelete, filename, onEdit }) => {
	const [isEditing, setIsEditing] = useState(false);
	const [fileName, setFileName] = useState(filename);

	const handleOnEdit = () => {
		setIsEditing(false);
		onEdit(fileName);
	};
	const handleFileName = (val) => setFileName(val);

	return (
		<div className="file">
			<div className="file__file">
				<Icon iconType="files" />
			</div>
			{
				isEditing ?
					<>
						<div className="file__input">		
							<Input value={fileName} onChange={handleFileName} placeholder="Input new file name..."/>
						</div>
						<div className="file__icons">
							<Icon iconType="check" onClick={handleOnEdit}/>
							<Icon iconType="close" onClick={() => setIsEditing(false)}/>
						</div>
					</>
				: 
					<>
						<div className="file__name">{filename}</div>
				    <div className="file__icons">
							<div className="file__delete">
								<Icon iconType="close" onClick={onDelete}/>
							</div>
							<div className="file__edit">
								<Icon iconType="pen" onClick={() => setIsEditing(true)}/>
							</div>
				    </div>
			    </>
			}
		</div>
	)
};
