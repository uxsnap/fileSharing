import React from 'react';

export default ({ iconType, fileName, link }) =>
  <a download={fileName} className={`iicon iicon-${iconType}`} href={link} />