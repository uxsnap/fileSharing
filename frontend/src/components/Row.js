import React from 'react';
import { checkClass } from "../utils";

export default ({ children, curClass }) => {
  return (
    <div className={checkClass('irow', curClass)}>
     {children}
    </div>
  )
}