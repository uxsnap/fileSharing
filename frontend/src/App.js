import React, { useState, useEffect } from 'react';
import { ErrorWrapper } from 'components';
import { Auth } from 'pages/Auth';
import { Profile } from 'pages/Profile';

function App() {
  const [userName, setUserName] = useState('');
  const [error, setError] = useState('');

  const checkPage = () => {
    setUserName(localStorage.getItem('USER_NAME'));
  };

  useEffect(() => {
    checkPage();
  }, []);

  return (
    <div className="App">
      <ErrorWrapper error={error} onClose={() => setError('')}>
        {userName ? <Profile onError={setError} userName={userName} /> : <Auth checkPage={checkPage}/>}
      </ErrorWrapper>
    </div>
  );
}

export default App;
