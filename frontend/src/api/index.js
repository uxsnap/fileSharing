export { onLogin, onRegister, onLogout, onResetPassword } from './auth';
export { getUserData, getUserFiles, uploadAvatar, getUserAvatar, getUsers } from './user';
export { uploadFile, deleteFile, editFile, downloadFileById } from './file';
export { sendFriendRequest, getAllFriends, deleteFriend, getFriendFiles, getFriendRequests, acceptFriendRequest,
declineFriendRequest } from './friend';