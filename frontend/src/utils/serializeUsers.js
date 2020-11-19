import getUserAvatar from './getUserAvatar';

export default (users) => users.map((item) => ({
	img: getUserAvatar(item), 
	name: item.userName,
	icon: 'plus' 
}));