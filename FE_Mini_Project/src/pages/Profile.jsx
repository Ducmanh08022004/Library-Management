import React, { useEffect, useState, useContext } from "react";
import api from "../services/api";
import { ToastContext } from "../components/ToastProvider";
import { AuthContext } from "../context/AuthContext";
import '../css/Profile.css';

export default function Profile() {
  const [profile, setProfile] = useState(null);
  const { showToast } = useContext(ToastContext);
  const { user } = useContext(AuthContext);

  useEffect(() => {
    if (!user) return;
    api.getUserById(user.id).then(res => setProfile(res.data.result));
  }, [user]);

  const handleSave = () => {
    api.updateUser(user.id, profile).then(() => {
      showToast("Cập nhật thông tin thành công");
    });
  };

  if (!profile) return <div>Loading...</div>;

  return (
    <div className="profile-page container">
  <h2>Thông tin cá nhân</h2>
  <div className="profile-form">
    <input
      value={profile.username}
      onChange={e => setProfile({ ...profile, username: e.target.value })}
      placeholder="Tên đăng nhập"
    />
    <input
      value={profile.fullName || ""}
      onChange={e => setProfile({ ...profile, fullName: e.target.value })}
      placeholder="Họ và tên"
    />
    <input
      value={profile.password || ""}
      type="password"
      onChange={e => setProfile({ ...profile, password: e.target.value })}
      placeholder="Mật khẩu"
    />
    <input
      value={profile.email || ""}
      onChange={e => setProfile({ ...profile, email: e.target.value })}
      placeholder="Email"
    />
    <button onClick={handleSave}>Lưu</button>
  </div>
</div>

  );
}
