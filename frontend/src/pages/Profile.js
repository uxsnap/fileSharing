import React, { useState, useEffect, useRef } from 'react';
import { Row, Col, Input, Icon, IconList, FileList, Button, AvatarItem, InputSelect, Avatar, NoInfo, SideMenu } from 'components';
import { 
  defaultResponseObject, 
  defaultStatusObject, 
  lazyRender,
  FILE_STATE, 
  getUserAvatar,
  serializeUsers,
  MIN_SEARCH_LENGTH
} from 'utils';
import { ApiService, AuthService, FriendService } from 'service';

export const Profile = ({ onError, onLogout }) => {
  const [fileState, setFileState] = useState(defaultStatusObject());
  const [avatarState, setAvatarState] = useState(defaultStatusObject());
  const [friendState, setFriendState] = useState(defaultStatusObject());

	const [infoList, setUserInfo] = useState(defaultResponseObject());
  const [fileItems, setUserFiles] = useState(defaultResponseObject());
  const [userAvatar, setUserAvatar] = useState(defaultResponseObject());
  const [friends, setUserFriends] = useState(defaultResponseObject());
  const [users, setUsers] = useState(defaultResponseObject());
  const [search, setSearch] = useState("");

  const fileRef = useRef(null);
  const avatarRef = useRef(null);

  const apiService = new ApiService(onError);
  const authService = new AuthService(onError);
  const friendService = new FriendService(onError);

  useEffect(() => {
    apiService.fetchUserData(setUserInfo);
    apiService.fetchUserFiles(setUserFiles);
    apiService.fetchUserAvatar(setUserAvatar);
    friendService.handleGetFriends(setUserFriends);
  }, [onError]);

  const addNewFile = (ref) => {
    ref.current.click();
  };

  const handleSetSearch = async (search) => {
    setSearch(search);
    await apiService.handleGetUsers(search, setUsers);
  };

  const onClickLogout = async () => {
    await authService.handleLogout();
    onLogout();
  };

  const onAvatarItemClick = async (name) => {
    await friendService.addFriend(name, setFriendState);
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
                Stub={NoInfo}
                items={serializeUsers(users.data, onAvatarItemClick)}
                checked={friendState.data}
                minLength={MIN_SEARCH_LENGTH}
                checkedIcon="check"
              /> 
  					</div>
            <div className="profile-header__logout">
              <Button onClick={onClickLogout}>Logout</Button>  
            </div>		
				  </div>
        </Col>
			</Row>
			<Row curClass="profile__main">
        <SideMenu>
          <ul>
            {Array(100).fill(100).map((item) => (
              <li>Lorem, ipsum dolor sit, amet consectetur adipisicing elit. Neque ducimus sit reiciendis, officiis sapiente accusantium tempore itaque tenetur eos maiores deleniti eum, illum cumque laudantium, ad iste quam eaque praesentium
              </li>
            ))}
          </ul>
        </SideMenu>  
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