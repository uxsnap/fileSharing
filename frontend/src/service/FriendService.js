import { addFriend, getAllFriends } from 'api';
import { defaultStatusObject, defaultResponseObject, RES_STATUS } from 'utils';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  addFriend = async (name, cb) =>  {
    cb(defaultResponseObject());
    try {
      const res = await addFriend(name);
      cb({
        data: res.data.users,
        status: res.status
      });
    } catch (error) {
      cb({
        ...defaultResponseObject(),
        status: RES_STATUS.ERROR
      });
      this.onError(error)
    }
  }


  handleGetFriends = async (cb) => {
    try {
      const res = await getAllFriends();
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
      this.onError(error);
    }
  };
}