import { getFriendFiles, downloadFileById } from 'api';
import { defaultResponseObject, RES_STATUS } from 'utils';
import FileDownload from 'js-file-download';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  downloadFile = async (fileId, fileName) => {
    const res = await downloadFileById(fileId);
    FileDownload(res.data, fileName);
  };

  fetchUserFiles = async (userId) => {
    try {
      const res = await getFriendFiles(userId);
      console.log(res);
      if (res.data) {
        res.data = res.data.files
          .map((file) => ({
            id: file.fileId,
            text: file.fileName,
            link: file.fileLink,
            iconType: 'download'
          }));
      }
      return {
        ...defaultResponseObject(),
        data: res.data,
        status: res.status
      };
      // return res;
    } catch (error) {
      this.onError(error);
      return {
        ...defaultResponseObject(),
        status: RES_STATUS.ERROR
      };
    }
  };
}