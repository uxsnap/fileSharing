import { getFriendFiles } from 'api';
import { defaultResponseObject, RES_STATUS } from 'utils';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  fetchUserFiles = async (userId, cb) => {
    try {
      const res = await getFriendFiles(userId);
      cb({
        ...defaultResponseObject(),
        data: res.data.files,
        status: res.status
      });
    } catch (error) {
      console.log('here');
      cb({
        ...defaultResponseObject(),
        status: RES_STATUS.ERROR
      });
      this.onError(error);
    }
  };
}