import React from 'react';
import { RES_STATUS } from "../utils";
import {Loader} from "./index";

export const LazyRender = ({ status, children, error = 'Error' }) => {
  return status === RES_STATUS.LOADING
    ? <Loader />
    : status === RES_STATUS.ERROR
    ? error
    : children;
};

export default LazyRender;