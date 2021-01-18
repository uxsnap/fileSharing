import OutsideClickHandler from 'react-outside-click-handler';
import React, { useState, useEffect, useRef } from 'react';
import isEqual from 'lodash.isequal';
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
  Loader,
  UserList,
  UserRequestItem,
  LazyRender,
  FileLoadWrapper
} from 'components';
import {
  defaultResponseObject,
  defaultStatusObject,
  getUserAvatar,
  MIN_SEARCH_LENGTH,
  serializeUserData,
  serializeRequestData
} from 'utils';
import { ApiService, AuthService, FriendService, FileService } from 'service';

export const Profile = ({ onError, onLogout }) => {
  const [fileState, setFileState] = useState(defaultStatusObject());
  const [avatarState, setAvatarState] = useState(defaultStatusObject());
  const [friendState, setFriendState] = useState(defaultStatusObject());
  const [friendDeleteState, setFriendDeleteState] = useState(defaultStatusObject());

  const [friendFiles, setFriendFilesState] = useState(defaultResponseObject());
  const [friendRequests, setFriendRequests] = useState(defaultResponseObject());
  const [infoList, setUserInfo] = useState(defaultResponseObject());
  const [fileItems, setUserFiles] = useState(defaultResponseObject());
  const [userAvatar, setUserAvatar] = useState(defaultResponseObject());
  const [friends, setUserFriends] = useState(defaultResponseObject());
  const [users, setUsers] = useState(defaultResponseObject());

  const [search, setSearch] = useState("");
  const [mouseOverId, setMouseOverId] = useState("");
  const [intervalId, setIntervalId] = useState(null);
  const [activeSideMenu, setActiveSideMenu] = useState(false);


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
    friendService.getFriends(setUserFriends);
  }, [onError]);

  const stepGetFriendRequests = async (firstStep = false) => {
    const res = await friendService.getFriendRequests();
    if (firstStep || !isEqual(res, friendRequests))
      setFriendRequests(res);
  };

  useEffect(() => {
    stepGetFriendRequests(true);
    const intervalId = setInterval(async () => {
      stepGetFriendRequests();
    }, 3000);
    setIntervalId(intervalId);
  }, []);

  const addNewFile = (ref) => ref.current.click();

  const handleSetSearch = (search) => {
    setSearch(search);
    search.length && apiService.handleGetUsers(search, setUsers);
  };

  const onClickLogout = async () => authService
    .handleLogout()
    .then(() => {
      clearInterval(intervalId);
      onLogout();
    });

  const onAvatarItemClick = (name) => friendService.sendFriendRequest(name, setFriendState);

  const onMouseEnter = async (id, selector) => {
    const res = await fileService.fetchUserFiles(id);
    setFriendFilesState(res);
    setMouseOverId(selector);
  };

  const onMouseLeave = () => !setMouseOverId(null) && setFriendFilesState(defaultResponseObject());

  const onActive = (active) => setActiveSideMenu(active);

  const checkActiveUserFiles = (id) =>
    mouseOverId && mouseOverId.includes(id);

  const handleFriendDelete = (id) =>
    friendService.deleteFriend(id, setFriendDeleteState, () => {
      friendService.getFriends(setUserFriends);
    });

  const handleFriendRequest = (id, status) => friendService.handleFriendRequest(
    id, 
    status, 
    () => friendService.getFriends(setUserFriends)
  );

  const onFileLoad = (fileId, fileName) => fileService.downloadFile(fileId, fileName);

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
                  items={serializeUserData(users.data, { onIconClick: onAvatarItemClick, icon: 'plus' })}
                  checked={friendState.data}
                  minLength={MIN_SEARCH_LENGTH}
                  checkedIcon="check"
                /> 
              </OutsideClickHandler>
  					</div>
            <div className="profile-header__friendRequests">
              <UserList 
                icon="user-friends" 
                items={serializeRequestData(friendRequests.data, {
                  onRequest: handleFriendRequest
                })}
                Component={UserRequestItem}
              />
            </div>
            <div className="profile-header__logout">
              <Button onClick={onClickLogout}>Logout</Button>  
            </div>		
				  </div>
        </Col>
			</Row>
			<Row curClass="profile__main">
        <SideMenu onActive={onActive} >
          {serializeUserData(friends.data, { 
            icon: 'close',
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
        <FilesContextMenu
          onLoad={onFileLoad}
          active={activeSideMenu}
          files={friendFiles.data}
          userId={mouseOverId}
          Loader={Loader}
          onMouseLeave={onMouseLeave}
          status={friendFiles.status}
        />
        <Col>
				  <div className="profile__me me">
          	<div>
    					<div className="me__avatar">
                <FileLoadWrapper ref={avatarRef} onChange={(event) => apiService.handleNewAvatar(
                  event,
                  setAvatarState,
                  () => apiService.fetchUserAvatar(setUserAvatar)
                )}>
                  <Avatar data={getUserAvatar(userAvatar.data)} onClick={() => addNewFile(avatarRef)} />
                </FileLoadWrapper>
              </div>
  					  <div className="me__info">
                <LazyRender status={infoList.status}>
                  <IconList items={infoList.data} />
                </LazyRender>
              </div>
              <div className="profile__add-file">
                <FileLoadWrapper ref={fileRef} onChange={(event) => apiService.handleFileUpload(
                  event,
                  setFileState,
                  () => apiService.fetchUserFiles(setUserFiles)
                )}>
                  <Button onClick={() => addNewFile(fileRef)}>Add new file</Button>
                </FileLoadWrapper>
              </div>
            </div>
				  </div>
        </Col>
				<div className="profile__files">
          <LazyRender status={fileItems.status}>
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
            />
          </LazyRender>
				</div>
			</Row>
		</div>
	);	
};