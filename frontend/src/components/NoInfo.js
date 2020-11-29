import React from 'react';
import {checkClass} from "../utils";

export default ({ className, children, style }) => (
  <div className={checkClass('no-info', className)} style={style}>
    {children ? children : "No information"}
  </div>
)