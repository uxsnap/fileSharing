import getUserAvatar from './getUserAvatar';

export default (friends, options) => {
  return friends && friends.length ? friends.map((item) => ({
    id: item.id,
    img: getUserAvatar(item), 
    name: item.userName,
    icon: options.active ? 'close' : '',
    onIconClick: options.onIconClick
  })) : [];
}