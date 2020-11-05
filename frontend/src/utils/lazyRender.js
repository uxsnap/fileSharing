import React from 'react';
import { RES_STATUS } from './index';
import { Loader } from '../components';

export default (Component, status) => {
	switch(status) {
		case RES_STATUS.LOADING:
			return <Loader />;
		case RES_STATUS.ERROR:
			return 'Error';
		case RES_STATUS.OK:
			return Component;
	}
};