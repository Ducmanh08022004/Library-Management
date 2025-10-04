import React, { useEffect, useState, useContext } from "react";
import api from "../services/api";
import { ToastContext } from "../components/ToastProvider";
import { AuthContext } from "../context/AuthContext";

export default function Notifications() {
  const [notifications, setNotifications] = useState([]);
  const { showToast } = useContext(ToastContext);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    if (!user) return;

    if (user.role === "ADMIN") {
      api.getNotifications().then(res => setNotifications(res.data.result));
    } else {
      api.getNotificationsByUser(user.id).then(res => setNotifications(res.data.result));
    }
  }, [user]);

  const handleDelete = (id) => {
    api.deleteNotification(id).then(() => {
      showToast("Xóa thông báo thành công");
      setNotifications(notifications.filter(n => n.id !== id));
    });
  };

  return (
    <div className="notifications-page container">
      <h2>Thông báo</h2>
      <ul className="notifications-list">
        {notifications.map(n => (
          <li key={n.id} className="notification-item" style={{ marginBottom: "8px" }}>
            <span className="notification-message">{n.message}</span>
            {user?.role === "ADMIN" && (
              <button
                onClick={() => handleDelete(n.id)}
                style={{
                  marginLeft: "8px",
                  padding: "4px 8px",
                  backgroundColor: "#dc3545",
                  color: "#fff",
                  border: "none",
                  borderRadius: "6px",
                  cursor: "pointer"
                }}
              >
                Xóa
              </button>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
