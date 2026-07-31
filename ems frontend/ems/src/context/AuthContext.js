import React, { createContext, useState, useContext } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [authState, setAuthState] = useState({
    isAdmin: localStorage.getItem('isAdmin') === 'true',
    userName: localStorage.getItem('userName') || 'John Doe',
    profileImage: localStorage.getItem('profileImage') || 'https://via.placeholder.com/40',
    userEmail: localStorage.getItem('userEmail') || 'N/A',
  });

  const updateAuthState = (newState) => {
    setAuthState((prev) => ({ ...prev, ...newState }));
  };

  return (
    <AuthContext.Provider value={{ authState, updateAuthState }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
