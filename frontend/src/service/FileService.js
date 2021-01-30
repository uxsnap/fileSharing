import {getFriendFiles, downloadFileById, uploadAvatar, uploadFile, editFile, deleteFile, getUserFiles} from 'api';
import { defaultResponseObject, promiseWrapResponse, RES_STATUS} from 'utils';
import FileDownload from 'js-file-download';
import { serializeUserFiles } from "../utils";

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  downloadFile = async (fileId, fileName) => {
    const res = await downloadFileById(fileId);
    FileDownload(res.data, fileName);
  };

  fetchUserFiles = (userId) =>
    promiseWrapResponse.call(this, getFriendFiles, serializeUserFiles, userId);

  handleFileUpload = (file) =>
    promiseWrapResponse.call(this, uploadFile, null, file);


  handleEditFile = (fileId, fileName) =>
    promiseWrapResponse.call(this, editFile, null, fileId, fileName);

  handleDeleteFile = (fileId) =>
    promiseWrapResponse.call(this, deleteFile, null, fileId);

}