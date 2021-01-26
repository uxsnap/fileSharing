import { 
  sendFriendRequest,
  getAllFriends,
  getFriendRequests,
  deleteFriend,
  acceptFriendRequest,
  declineFriendRequest
} from 'api';
import {
  FRIEND_REQUEST_STATUS,
  promiseWrapResponse
} from 'utils';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  sendFriendRequest = (userId) => promiseWrapResponse.call(this, sendFriendRequest, null, userId);

  deleteFriend = (id) =>  promiseWrapResponse.call(this, deleteFriend, null, id);

  getFriends = () => promiseWrapResponse.call(this, getAllFriends, null);


  getFriendRequests = () =>  promiseWrapResponse.call(this, getFriendRequests, null);

  handleFriendRequest = (id, status) => {
    let neededCbFunc;
    switch(status) {
      case FRIEND_REQUEST_STATUS.APPROVED:
        neededCbFunc = acceptFriendRequest;
        break;
      case FRIEND_REQUEST_STATUS.DECLINED:
        neededCbFunc = declineFriendRequest;
        break;
    }
    return promiseWrapResponse.call(this, neededCbFunc, null, id);
  };
}