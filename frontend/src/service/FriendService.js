import { sendFriendRequest, getAllFriends, deleteFriend } from 'api';
import { defaultStatusObject, defaultResponseObject, RES_STATUS } from 'utils';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  sendFriendRequest = async (name, cb) =>  {
    cb(defaultResponseObject());
    try {
      const res = await sendFriendRequest(name);
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

  deleteFriend = async (id, cb, successCb) =>  {
    cb(defaultResponseObject());
    try {
      const res = await deleteFriend(id);
      cb({
        status: res.status
      });
      res.status === RES_STATUS.OK && successCb();
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