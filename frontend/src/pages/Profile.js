import OutsideClickHandler from 'react-outside-click-handler';
import { RES_STATUS } from 'utils';
import React, { useState, useEffect, useRef } from 'react';
import { 
  Row, 
  Col, 
  Icon, 
  IconList,
  FileList,
  Button,
  AvatarItem,
  InputSelect,
  Avatar,
  NoInfo,
  SideMenu,
  UserFilesProfile,
  FilesContextMenu,
  Loader
} from 'components';
import { 
  defaultResponseObject, 
  defaultStatusObject, 
  lazyRender,
  FILE_STATE, 
  getUserAvatar,
  serializeUsers,
  serializeFriends,
  MIN_SEARCH_LENGTH
} from 'utils';
import { ApiService, AuthService, FriendService, FileService } from 'service';

export const Profile = ({ onError, onLogout }) => {
  const [fileState, setFileState] = useState(defaultStatusObject());
  const [avatarState, setAvatarState] = useState(defaultStatusObject());
  const [friendState, setFriendState] = useState(defaultStatusObject());
  const [friendDeleteState, setFriendDeleteState] = useState(defaultStatusObject());
  const [friendFiles, setFriendFilesState] = useState(defaultResponseObject());

	const [infoList, setUserInfo] = useState(defaultResponseObject());
  const [fileItems, setUserFiles] = useState(defaultResponseObject());
  const [userAvatar, setUserAvatar] = useState(defaultResponseObject());
  const [friends, setUserFriends] = useState(defaultResponseObject());
  const [users, setUsers] = useState(defaultResponseObject());
  const [search, setSearch] = useState("");
  const [activeSideMenu, setActiveSideMenu] = useState(false);
  const [mouseOverId, setMouseOverId] = useState("");

  const fileRef = useRef(null);
  const avatarRef = useRef(null);

  const apiService = new ApiService(onError);
  const authService = new AuthService(onError);
  const friendService = new FriendService(onError);
  const fileService = new FileService(onError);

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
    search.length && await apiService.handleGetUsers(search, setUsers);
  };

  const onClickLogout = async () => {
    await authService.handleLogout();
    onLogout();
  };

  const onAvatarItemClick = async (name) => {
    await friendService.sendFriendRequest(name, setFriendState);
  };

  const onMouseEnter = (id) => {
    setMouseOverId(id);
    fileService.fetchUserFiles(id, setFriendFilesState);
  };

  const onMouseLeave = () => {
    setMouseOverId(null);
    setFriendFilesState(defaultResponseObject());
  }

  const onActive = (active) => setActiveSideMenu(active);

  const checkActiveUserFiles = (id) => {
    return mouseOverId && mouseOverId.includes(id);
  };

  const handleFriendDelete = async (id) => {
    await friendService.deleteFriend(id, setFriendDeleteState, () => {
      friendService.handleGetFriends(setUserFriends);
    });
  };

  console.log()

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
              <OutsideClickHandler onOutsideClick={() => handleSetSearch('')}>
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
              </OutsideClickHandler>
  					</div>
            <div class="profile-header__friendRequests">
              <Icon iconType="person" />
            </div>
            <div className="profile-header__logout">
              <Button onClick={onClickLogout}>Logout</Button>  
            </div>		
				  </div>
        </Col>
			</Row>
			<Row curClass="profile__main">
        <SideMenu onActive={onActive} >
          {serializeFriends(friends.data, { 
            active: activeSideMenu,
            onIconClick: handleFriendDelete 
          }).map((item) => (
            <UserFilesProfile 
              user={item} 
              onMouseEnter={onMouseEnter} 
              active={checkActiveUserFiles(item.id)}
              deleteUser={handleFriendDelete}
            />
          ))}
        </SideMenu>  
        {mouseOverId && 
          <FilesContextMenu
            active={activeSideMenu} 
            files={friendFiles.data}
            userId={mouseOverId}
            Loader={Loader}
            onMouseLeave={onMouseLeave}
            status={friendFiles.status}
          />
        }
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