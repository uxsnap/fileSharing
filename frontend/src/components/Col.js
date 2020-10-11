import React from 'react';
import {checkClass} from "../utils";

export default ({ className, children, style }) => (
  <div className={checkClass('icol', className)}>
    {children}
  </div>
)