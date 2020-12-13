import queryExecute from './queryExecute';
import axios from 'axios';
// import { mapToRestArray } from 'u'

export const addFriend = (name) => {
  const token = localStorage.getItem('TOKEN');
  return axios({
    method: 'post',
    url: process.env.REACT_APP_BASE_URL + `/friend/add`,
    data: { users: [name] },
    headers: { 
      'Authorization': `Bearer ${token}`, 
    }
  });
};

export const getAllFriends = () => {
  return queryExecute('get', '/friend/all');
};

export const deleteFriend = (friendId) => {
  return queryExecute('delete', `/friend/delete/${friendId}`);
};