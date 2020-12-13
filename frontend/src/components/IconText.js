import React from 'react';
import { Icon, Row } from './index';

export default ({ icon, text }) => {
  return (
    <div className="icon-text">
      <Row>
        <div>{text}</div>
        <Icon iconType={icon} />
      </Row>
    </div>
  )
};