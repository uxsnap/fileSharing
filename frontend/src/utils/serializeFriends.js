import getUserAvatar from './getUserAvatar';

export default (friends) => {
  return friends && friends.length ? friends.map((item) => ({
    id: item.id,
    img: getUserAvatar(item), 
    name: item.userName,
  })) : [];
}