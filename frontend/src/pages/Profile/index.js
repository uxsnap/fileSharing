import React, { useState, useEffect, useRef } from 'react';
import { Row, Col, Input, Icon, IconList, FileList, Button } from 'components';
import { defaultResponseObject, defaultStatusObject, lazyRender, FILE_STATE } from 'utils';
import { 
  createPersonInfoList, 
  fetchUserData, 
  fetchUserFiles,
  handleFileUpload 
} from './helpers';

export const Profile = (props) => {
  const [fileState, setFileState] = useState(defaultStatusObject());

	const [infoList, setUserInfo] = useState(defaultResponseObject());
  const [fileItems, setUserFiles] = useState(defaultResponseObject());
  const [search, setSearch] = useState("");
  const [img, setImg] = useState("");

  const fileRef = useRef(null);

  useEffect(() => {
    fetchUserData(props.userName, setUserInfo);
    fetchUserFiles(props.userName, setUserFiles);
  }, [props.userName]);

  // const fileItems = [
  //   { name: 'filename.jpg', id: 1 },
  //   { name: 'filename.jpg', id: 1 },
  //   { name: 'filename.jpg', id: 1 },
  //   { name: 'filename.jpg', id: 1 },
  //   { name: 'filename.jpg', id: 1 },
  // ];


  const addNewFile = () => {
    fileRef.current.click();
  };

	return (
		<div className="profile">
			<Row>
        <Col>  
  				<div className="profile__header profile-header">
  					<div className="profile-header__brand">
              <Icon iconType="files" />
              <span className="profile-header__name">FILES</span>
  					</div>		
  					<div className="profile-header__search">
  						<Input 
                value={search} 
                onChange={setSearch}
                placeholder="Search for friends"
                rightIcon="loupe"
              />
  					</div>		
				  </div>
        </Col>
			</Row>
			<Row curClass="profile__main">
        <Col>
				  <div className="profile__me me">
          	<div>
    					<div className="me__avatar">
                {img.length
                  ? <img src={img} alt="ME"/>
                  : <Icon iconType="person" />
                }
              </div>
  					  <div className="me__info">
                {lazyRender(<IconList items={infoList.data} />, infoList.status)}
              </div>
              <div className="profile__add-file">
                <input type="file" name="file" ref={fileRef} onChange={(event) => handleFileUpload(event, setFileState, () => {
                  fetchUserFiles(props.userName, setUserFiles);
                })}/>
                <Button onClick={addNewFile}>Add new file</Button>
              </div> 
            </div>
				  </div>
        </Col>
				<div className="profile__files">
          {lazyRender(<FileList items={fileItems} onDelete={() => false} onEdit={() => false}/>, -1)}
				</div>
			</Row>
		</div>
	);	
};