import getUserAvatar from './getUserAvatar';

export default (users, click) => {
  return users && users.length ? users.map((item) => ({
    id: item.id,
  	img: getUserAvatar(item), 
  	name: item.userName,
  	icon: 'plus',
    onIconClick: click 
  })) : [];
}