import { useEffect, useState } from 'react';
import isEqual from "lodash.isequal";
import {defaultResponseObject} from "./index";

export default (intervalObj, friendService) => {
  const [friendRequests, setFriendRequests] = useState(defaultResponseObject());

  const stepGetFriendRequests = async (firstStep = false) => {
    const res = await friendService.getFriendRequests();
    if (firstStep || !isEqual(res, friendRequests))
      setFriendRequests(res);
  };

  useEffect(async () => {
    await stepGetFriendRequests(true);
    intervalObj.intervalId = setInterval(async () => {
      await stepGetFriendRequests();
    }, 3000);
  }, []);

  return [intervalObj, friendRequests];
};