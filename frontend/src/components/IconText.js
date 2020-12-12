import React from 'react';
import { Icon, Row } from './index';

export default ({ icon, text }) => {
  return (
    <div class="icon-text">
      <Row>
        <Icon iconType={icon} />
        <span>{text}</span>
      </Row>
    </div>
  )
};