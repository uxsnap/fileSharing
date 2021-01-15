import queryExecute from './queryExecute';
import axios from 'axios';
// import { mapToRestArray } from 'u'

export const sendFriendRequest = (userId) => {
  const token = localStorage.getItem('TOKEN');
  return axios({
    method: 'post',
    url: process.env.REACT_APP_BASE_URL + `/friend/sendRequest`,
    data: { users: [userId] },
    headers: { 
      'Authorization': `Bearer ${token}`, 
    }
  });
};

export const getAllFriends = () => {
  return queryExecute('get', '/friend/all');
};

export const getFriendRequests = () => {
  return queryExecute('get', '/friend/requests');
};

export const deleteFriend = (friendId) => {
  return queryExecute('delete', `/friend/${friendId}/delete`);
};

export const getFriendFiles = async (userId) => {
  return queryExecute('get', `/friend/${userId}/files`);
};

export const acceptFriendRequest = async (userId) => {
  return queryExecute('get', `/friend/${userId}/add`);
};

export const declineFriendRequest = async (userId) => {
  return queryExecute('get', `/friend/${userId}/decline`);
};