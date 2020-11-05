import React from "react";

export default ({ children, type, onClick }) => (
  <button className={`ibutton ${type ? `ibutton_${type}` : ''}`} onClick={onClick}>
    {children ? children : ''}
  </button>
);