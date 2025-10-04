import React, { createContext, useState } from "react";
import { v4 as uuidv4 } from "uuid"; // import uuid
import '../css/ToastProvider.css';

export const ToastContext = createContext();

export default function ToastProvider({ children }) {
  const [toasts, setToasts] = useState([]);

  function showToast(msg, type = "info") {
    const id = uuidv4(); // dùng uuid thay vì Date.now()
    setToasts((t) => [...t, { id, msg, type }]);
    setTimeout(
      () => setToasts((t) => t.filter((x) => x.id !== id)),
      4000
    );
  }

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}
      <div className="toast-container">
        {toasts.map((t) => (
          <div key={t.id} className={`toast toast-${t.type}`}>
            {t.msg}
          </div>
        ))}
      </div>
    </ToastContext.Provider>
  );
}
