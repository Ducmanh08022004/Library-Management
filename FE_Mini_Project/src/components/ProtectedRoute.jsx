import React, { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

export default function ProtectedRoute({ children }) {
  const { token } = useContext(AuthContext);

  if (!token) {
    // Nếu chưa có token → quay về trang login
    return <Navigate to="/login" replace />;
  }

  return children;
}
