import queryExecute from './queryExecute';
// import { mapToRestArray } from 'u'

export const addFriend = (name) => {
  return queryExecute('post', '/friend/add', { users: [name] });
};

export const getAllFriends = () => {
  return queryExecute('get', '/friend/all');
};