import React, { useState, useEffect } from 'react';
import { Auth } from './pages/Auth';
import { Profile } from './pages/Profile';

function App() {
  const [userName, setUserName] = useState('');

  const checkPage = () => {
    setUserName(localStorage.getItem('USER_NAME'));
  };

  useEffect(() => {
    checkPage();
  }, []);

  return (
    <div className="App">
      {userName ? <Profile userName={userName} /> : <Auth checkPage={checkPage}/>}
    </div>
  );
}

export default App;
