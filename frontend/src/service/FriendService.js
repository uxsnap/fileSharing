import { addFriend } from 'api';
import { defaultStatusObject } from 'utils';

export default class {
  constructor(onError) {
    this.onError = onError;
  }

  async addFriend(name, cb) {
    cb(defaultStatusObject());
    try {
      const res = await addFriend(name);
      cb({
        status: res.status
      })
    } catch (error) {
      this.onError(error)
    }
  }


  async allFriends(cb) {
    cb(defaultStatusObject)
  }
}