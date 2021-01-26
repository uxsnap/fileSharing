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
} from 'components';
import {
  defaultResponseObject,
  defaultStatusObject,
  getUserAvatar,
  MIN_SEARCH_LENGTH,
  serializeUserData,
  serializeRequestData, RES_STATUS
} from 'utils';
import { UserService, AuthService, FriendService, FileService } from 'service';

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

  const userService = new UserService(onError);
  const authService = new AuthService(onError);
  const friendService = new FriendService(onError);
  const fileService = new FileService(onError);

  useEffect(() => {
    userService.fetchUserData()
      .then(setUserInfo);
    userService.fetchUserFiles()
      .then(setUserFiles);
    userService.fetchUserAvatar()
      .then(setUserAvatar);
    friendService.getFriends()
      .then(setUserFriends);
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
    search.length && userService.handleGetUsers(search).then((res) => {
      setUsers(res.data);
    });
  };

  const onClickLogout = async () => authService
    .handleLogout()
    .then(() => {
      clearInterval(intervalId);
      onLogout();
    });

  const onAvatarItemClick = (name) => {
    setFriendState(defaultResponseObject());
    friendService.sendFriendRequest(name).then((res) => {
      setFriendState(res.data);
    });
  }

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
    friendService.deleteFriend(id)
      .then((statusData) => {
        setFriendDeleteState(statusData);
        return friendService.getFriends();
      })
      .then((friendData) => {
        setUserFriends(friendData);
      });

  const handleFriendRequest = (id, status) => friendService.handleFriendRequest(id, status)
      .then(() => friendService.getFriends(setUserFriends));

  const handleAvatarUpload = (event) => {
    if (!event.target.files.length) {
      return setAvatarState({ ...avatarState, status: RES_STATUS.OK });
    }
    const file = event.target.files[0];
    return userService.handleNewAvatar(file)
      .then((res) => {
        setAvatarState(res.data);
        userService.fetchUserAvatar(setUserAvatar);
      })
  }

  const handleFileUpload = (event) => {
    if (!event.target.files.length) {
      return setFileState({ ...fileState, status: RES_STATUS.OK });
    }
    const file = event.target.files[0];
    return fileService.handleFileUpload(file)
      .then((res) => {
        setFileState(res.data);
        return userService.fetchUserFiles()
      })
      .then(setUserFiles);
  };

  const handleDeleteFile = (id) =>
    fileService.handleDeleteFile(id, fileItems).then((res) => {
      setUserFiles({ ...fileItems,
        data: fileItems.data.filter((file) => file.id !== id) });
    });

  const handleEditFile = (id, fileName) =>
    fileService.handleEditFile(id, fileName, fileItems).then((res) => {
      setUserFiles({ ...fileItems,
        data: fileItems.data.filter((file) => file.id === id ? {...file, name: fileName } : file) });
    })

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
                <input type="file" name="file" ref={avatarRef} onChange={handleAvatarUpload} />
                <Avatar data={getUserAvatar(userAvatar.data)} onClick={() => addNewFile(avatarRef)} />
              </div>
  					  <div className="me__info">
                <LazyRender status={infoList.status}>
                  <IconList items={infoList.data} />
                </LazyRender>
              </div>
              <div className="profile__add-file">
                <input type="file" name="file" ref={fileRef} onChange={handleFileUpload} />
                <Button onClick={() => addNewFile(fileRef)}>Add new file</Button>
              </div>
            </div>
				  </div>
        </Col>
				<div className="profile__files">
          <LazyRender status={fileItems.status}>
            <FileList items={fileItems.data}
              onDelete={handleDeleteFile}
              onEdit={handleEditFile}
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