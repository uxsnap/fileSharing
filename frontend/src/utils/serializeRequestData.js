import getUserAvatar from './getUserAvatar';
import { FRIEND_REQUEST_STATUS } from '.';

export default (items, options) => {
  return items && items.length ? items.map((item) => ({
    id: item.id,
    img: getUserAvatar(item), 
    text: item.userName,
    icons: [
      {icon: 'check', onClick: () => options.onRequest(item.id, FRIEND_REQUEST_STATUS.APPROVED) }, 
      {icon: 'close', onClick: () => options.onRequest(item.id, FRIEND_REQUEST_STATUS.DECLINED) }
    ],
  })) : [];
}