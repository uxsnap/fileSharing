import React from 'react';

export const FileLoadWrapper = ({ ref, children, onChange }) => {
  return (
    <>
      <input type="file" name="file" ref={ref} onChange={onChange}/>
      {children}
    </>
  );
};

export default FileLoadWrapper;