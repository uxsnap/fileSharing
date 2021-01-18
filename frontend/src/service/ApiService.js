import { defaultResponseObject, RES_STATUS, createPersonInfoList, createPersonFilesList } from 'utils';
import { getUserData, getUserFiles, uploadFile, deleteFile, editFile, uploadAvatar, getUserAvatar, getUsers } from 'api';

export default class {
	constructor(onError) {
		this.onError = onError;
  }

  handleGetUsers = async (search, cb) => {
  	try {
  		const res = await getUsers(search);
  		cb({
	      ...defaultResponseObject(),
	      data: res.data.users,
      	status: res.status
	    });
  	} catch (error) {
  		cb({
	      ...defaultResponseObject(),
      	status: RES_STATUS.ERROR
	    });
	    this.onError(error.response);
  	}
  };

  handleNewAvatar = async (event, cb, successCb) => {
		if (!event.target.files.length) {
	    cb(RES_STATUS.OK);
	    return;
	  }
	  const file = event.target.files[0];
	  cb(RES_STATUS.LOADING);
	  try {
	    const res = await uploadAvatar(file);
	    cb(RES_STATUS.OK);
	    successCb();
	  } catch (error) {
	    cb(RES_STATUS.ERROR);
	    this.onError('Avatar upload error');
	  }
  };

  handleFileUpload = async (event, cb, successCb) => {
	  if (!event.target.files.length) {
	    cb(RES_STATUS.OK);
	    return;
	  }
	  const file = event.target.files[0];
	  cb(RES_STATUS.LOADING);
	  try {
	    const res = await uploadFile(file);
	    cb(RES_STATUS.OK);
	    successCb();
	  } catch (error) {
	    cb(RES_STATUS.ERROR);
	    this.onError(error.response);
	  }
	};


	handleEditFile = async (fileId, fileName, files, cb) => {
	  try {
	    await editFile(fileId, fileName);
	    cb({
	       ...defaultResponseObject(),
	      data: files.data.map((file) => file.id === fileId ? {...file, name: fileName } : file), 
	      status: RES_STATUS.OK
	    });
	  } catch (error) {
	     cb({
	       ...defaultResponseObject(), 
	      data: files, 
	      status: RES_STATUS.ERROR
	    });
	     this.onError(error.response);
	  }
	};

  handleDeleteFile = async (fileId, files, cb) => {
	  try {
	    await deleteFile(fileId);
	    cb({
	       ...defaultResponseObject(),
	      data: files.data.filter((file) => file.id !== fileId), 
	      status: RES_STATUS.OK
	    });
	  } catch (error) {
	    cb({
	       ...defaultResponseObject(), 
	      data: files, 
	      status: RES_STATUS.ERROR
	    });
      this.onError(error.response);
	  }
	}

	fetchUserData = async (cb) => {
	  try {
	   	const res = await getUserData();
	    cb({ 
	      ...defaultResponseObject(), 
	      data: createPersonInfoList(res.data), 
	      status: res.status
	    });
	  } catch (error) {
	    cb({ status: RES_STATUS.ERROR });
	    this.onError(error.response);
	  }
	}


	fetchUserFiles = async (cb) => {
	  try {
	    const res = await getUserFiles();
	    cb({ 
	      ...defaultResponseObject(), 
	      data: createPersonFilesList(res.data), 
	      status: res.status
	    });
	  } catch (error) {
	    cb({ status: RES_STATUS.ERROR });
	    this.onError(error.response);
	  }
	}


	fetchUserAvatar = async (cb) => {
		try {
	    const res = await getUserAvatar();
	    cb({ 
	      ...defaultResponseObject(), 
	      data: res.data, 
	      status: res.status
	    });
	  } catch (error) {
	    cb({ status: RES_STATUS.ERROR });
	    this.onError(error.response);
	  }
	}
}