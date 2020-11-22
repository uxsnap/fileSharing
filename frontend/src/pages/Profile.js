import React, { useState, useEffect, useRef } from 'react';
import { Row, Col, Input, Icon, IconList, FileList, Button, AvatarItem, InputSelect, Avatar } from 'components';
import { defaultResponseObject, defaultStatusObject, lazyRender, FILE_STATE, getUserAvatar, serializeUsers } from 'utils';
import ApiService from 'service/ApiService';

export const Profile = ({ userName, onError }) => {
  const [fileState, setFileState] = useState(defaultStatusObject());
  const [avatarState, setAvatarState] = useState(defaultStatusObject());

	const [infoList, setUserInfo] = useState(defaultResponseObject());
  const [fileItems, setUserFiles] = useState(defaultResponseObject());
  const [userAvatar, setUserAvatar] = useState(defaultResponseObject());
  const [users, setUsers] = useState(defaultResponseObject());
  const [search, setSearch] = useState("");

  const fileRef = useRef(null);
  const avatarRef = useRef(null);

  const apiService = new ApiService(onError);

  useEffect(() => {
    apiService.fetchUserData(setUserInfo);
    apiService.fetchUserFiles(setUserFiles);
    apiService.fetchUserAvatar(setUserAvatar);
  }, [userName, onError]);

  const addNewFile = (ref) => {
    ref.current.click();
  };

  const handleSetSearch = async (search) => {
    setSearch(search);
    await apiService.handleGetUsers(search, setUsers);
  };

  const onLogout = async () => {
    await apiService.handleLogout();
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
              <InputSelect 
                value={search}
                onChange={handleSetSearch}
                placeholder="Search for friends"
                rightIcon="loupe"
                Component={AvatarItem}
                items={users && users.length ? serializeUsers(users) : []}
              /> 
  					</div>
            <div className="profile-header__logout">
              <Button onClick={onLogout}>Logout</Button>  
            </div>		
				  </div>
        </Col>
			</Row>
			<Row curClass="profile__main">
        <Col>
				  <div className="profile__me me">
          	<div>
    					<div className="me__avatar">
                <input type="file" name="file" ref={avatarRef} onChange={(event) => apiService.handleNewAvatar(
                  event, 
                  setAvatarState,
                  () => apiService.fetchUserAvatar(setUserAvatar)
                )}/>
                <Avatar data={getUserAvatar(userAvatar.data)} onClick={() => addNewFile(avatarRef)} />
              </div>
  					  <div className="me__info">
                {lazyRender(<IconList items={infoList.data} />, infoList.status)}
              </div>
              <div className="profile__add-file">
                <input type="file" name="file" ref={fileRef} onChange={(event) => apiService.handleFileUpload(
                  event, 
                  setFileState, 
                  () => apiService.fetchUserFiles(setUserFiles)
                )}/>
                <Button onClick={() => addNewFile(fileRef)}>Add new file</Button>
              </div> 
            </div>
				  </div>
        </Col>
				<div className="profile__files">
          {lazyRender(
            <FileList items={fileItems.data} 
              onDelete={
                (id) => apiService.handleDeleteFile(id, fileItems, setUserFiles)
              } 
              onEdit={
                (id, fileName) => apiService.handleEditFile(id, fileName, fileItems, setUserFiles)
              }
              stub={
                <span className="profile__no-items">You have 0 files</span>
              }
            />, 
            fileItems.status
          )}
				</div>
			</Row>
		</div>
	);	
};