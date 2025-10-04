import React, { createContext, useState, useEffect } from "react";
import api from "../services/api";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    if (token) {
      api.getInfo("/user/myInfo", {
        headers: { Authorization: `Bearer ${token}` }
      }).then(res => {
        setUser(res.data.result);
      }).catch(() => {
        setUser(null);
        setToken(null);
        localStorage.removeItem("token");
      });
    }
  }, [token]);

  const login = (tokenValue) => {
    setToken(tokenValue);
    localStorage.setItem("token", tokenValue);
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem("token");
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
