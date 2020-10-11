import React from "react";
import {checkClass} from "../utils";

export default ({ src, onClick, className }) => (
  <div onClick={onClick ? onClick : undefined} className={checkClass('ilogo__wrapper', className)}>
    <img className="ilogo" src={src} alt="LOGO"/>
  </div>
)