import { 
  sendFriendRequest,
  getAllFriends,
  getFriendRequests,
  deleteFriend,
  acceptFriendRequest,
  declineFriendRequest
} from 'api';
import { defaultStatusObject, defaultResponseObject, RES_STATUS, FRIEND_REQUEST_STATUS } from 'utils';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  sendFriendRequest = async (userId, cb) =>  {
    cb(defaultResponseObject());
    try {
      const res = await sendFriendRequest(userId);
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


  getFriends = async (cb) => {
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


  getFriendRequests = async () => {
    try {
      const res = await getFriendRequests();
      return {
      ...defaultResponseObject(),
        data: res.data.users,
        status: res.status
      };
    } catch (error) {
      this.onError(error);
      return {
        ...defaultResponseObject(),
        status: RES_STATUS.ERROR
      };
    }
  };

  handleFriendRequest = async (id, status, cb) => {
    let neededCbFunc;
    switch(status) {
      case FRIEND_REQUEST_STATUS.APPROVED:
        neededCbFunc = acceptFriendRequest;
        break;
      case FRIEND_REQUEST_STATUS.DECLINED:
        neededCbFunc = declineFriendRequest;
        break;
    }

    try {
      const res = await neededCbFunc(id);
      res.status === RES_STATUS.OK && cb();
    } catch (error) {
      this.onError(error)
    }
  };
}