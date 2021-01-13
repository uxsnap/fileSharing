import queryExecute from './queryExecute';
import axios from 'axios';
// import { mapToRestArray } from 'u'

export const sendFriendRequest = (name) => {
  const token = localStorage.getItem('TOKEN');
  return axios({
    method: 'post',
    url: process.env.REACT_APP_BASE_URL + `/friend/sendRequest`,
    data: { users: [name] },
    headers: { 
      'Authorization': `Bearer ${token}`, 
    }
  });
};

// export const declientFriendRequest = (name)

export const getAllFriends = () => {
  return queryExecute('get', '/friend/all');
};

export const getFriendRequests = () => {
  return queryExecute('get', '/friend/requests');
};

export const deleteFriend = (friendId) => {
  return queryExecute('delete', `/friend/delete/${friendId}`);
};

export const getFriendFiles = async (userId) => {
  return queryExecute('get', `/friend/${userId}/files`)
};