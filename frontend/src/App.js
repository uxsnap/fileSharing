import React, { useState, useEffect } from 'react';
import { ErrorWrapper } from 'components';
import { Auth } from 'pages/Auth';
import { Profile } from 'pages/Profile';

function App() {
  const [token, setToken] = useState('');
  const [error, setError] = useState('');

  const checkPage = () => {
    setToken(localStorage.getItem('TOKEN'));
  };

  useEffect(() => {
    checkPage();
  }, []);

  return (
    <div className="App">
      <ErrorWrapper error={error} onClose={() => setError('')}>
        {token ? <Profile onError={setError} onLogout={checkPage} /> : <Auth checkPage={checkPage}/>}
      </ErrorWrapper>
    </div>
  );
}

export default App;
