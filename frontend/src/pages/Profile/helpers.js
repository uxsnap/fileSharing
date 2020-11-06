import { defaultResponseObject, RES_STATUS } from 'utils';
import { getUserData, getUserFiles, uploadFile, deleteFile } from 'api';

export const createPersonInfoList = (personObj) => {
	return [
		{	label: personObj.userName, icon: 'person' }
	]
};

export const createPersonFilesList = (filesObj) => {
  return filesObj.files.map((file) => ({
    id: file.id,
    name: file.originalName
  }));
};

export const fetchUserData = async (userName, cb) => {
  try {
   	const res = await getUserData(userName);
    cb({ 
      ...defaultResponseObject(), 
      data: createPersonInfoList(res.data), 
      status: res.status
    });

  } catch (error) {
    cb({ error, status: RES_STATUS.ERROR });
  }
};


export const fetchUserFiles = async (userName, cb) => {
  try {
    const res = await getUserFiles(userName);
    cb({ 
      ...defaultResponseObject(), 
      data: createPersonFilesList(res.data), 
      status: res.status
    });
  } catch (error) {
    cb({ error, status: RES_STATUS.ERROR });
  }
};

export const handleFileUpload = async (userName, event, cb, successCb) => {
  if (!event.target.files.length) {
    cb(RES_STATUS.OK);
    return;
  }
  const file = event.target.files[0];
  cb(RES_STATUS.LOADING);
  try {
    const res = await uploadFile(userName, file);
    cb(RES_STATUS.OK);
    successCb();
  } catch (error) {
    cb(RES_STATUS.ERROR);
  }
};

export const handleDeleteFile = async (fileId, files, cb) => {
  try {
    await deleteFile(fileId);
    cb({
       ...defaultResponseObject(), 
      data: files.filter((file) => file.id !== fileId), 
      status: RES_STATUS.OK
    })
  } catch (error) {
    cb({
       ...defaultResponseObject(), 
      data: files, 
      status: RES_STATUS.ERROR
    })
  }
};
