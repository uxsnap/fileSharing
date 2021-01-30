import {
	defaultResponseObject,
	RES_STATUS,
	createPersonInfoList,
	createPersonFilesList,
	promiseWrapResponse
} from 'utils';
import { getUserData, getUserFiles, uploadAvatar, getUserAvatar, getUsers } from 'api';

export default class {
	constructor(onError) {
		this.onError = onError;
  }

	handleNewAvatar = (file) =>
		promiseWrapResponse.call(this, uploadAvatar, null, file)

  handleGetUsers =  (search) =>
		promiseWrapResponse.call(this, getUsers, null, search)

	fetchUserData = () =>
		promiseWrapResponse.call(this, getUserData, createPersonInfoList);

	fetchUserAvatar = () =>
		promiseWrapResponse.call(this, getUserAvatar, null);

	fetchUserFiles = () =>
		promiseWrapResponse.call(this, getUserFiles, createPersonFilesList);
}