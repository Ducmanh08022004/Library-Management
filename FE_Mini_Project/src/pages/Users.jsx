import React, { useEffect, useState, useContext } from "react";
import api from "../services/api";
import { ToastContext } from "../components/ToastProvider";
import { AuthContext } from "../context/AuthContext";
import Pagination from "../components/Pagination";
import "../css/User.css";

export default function Users() {
  const [users, setUsers] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [searchKey, setSearchKey] = useState("");
  const [page, setPage] = useState(1);
  const perPage = 10;

  const { showToast } = useContext(ToastContext);
  const { user, token } = useContext(AuthContext);

  useEffect(() => {
    if (!user) return;
    if (user.role === "ADMIN") {
      api.getUsers(token).then((res) => {
        setUsers(res.data.result);
        setFilteredUsers(res.data.result);
      });
    }
  }, [user, token]);

  const handleDelete = (id) => {
    if (!window.confirm("Bạn có chắc chắn muốn xóa người dùng này không?")) {
      return;
    }
    api.deleteUser(id, token).then(() => {
      showToast("Xóa người dùng thành công");
      const updated = users.filter((u) => u.id !== id);
      setUsers(updated);
      setFilteredUsers(updated);
      if (selectedUser?.id === id) setSelectedUser(null);
    });
  };

  // Tìm kiếm
  const handleSearch = (e) => {
    const key = e.target.value.toLowerCase();
    setSearchKey(key);
    if (!key) {
      setFilteredUsers(users);
    } else {
      setFilteredUsers(
        users.filter(
          (u) =>
            u.username.toLowerCase().includes(key) ||
            (u.email && u.email.toLowerCase().includes(key))
        )
      );
    }
    setPage(1); // reset về trang đầu khi tìm kiếm
  };

  // Phân trang
  const total = filteredUsers.length;
  const visible = filteredUsers.slice((page - 1) * perPage, page * perPage);

  return (
    <div className="users-page container">
      <h2>Người dùng</h2>

      {/* Thanh tìm kiếm */}
      <div className="search-bar card">
        <input
          type="text"
          placeholder="Tìm theo username hoặc email..."
          value={searchKey}
          onChange={handleSearch}
          className="search-input"
        />
      </div>

      <ul className="users-list">
        {visible.map((u) => (
          <React.Fragment key={u.id}>
            <li
              className={`user-item ${
                selectedUser?.id === u.id ? "active" : ""
              }`}
              onClick={() =>
                setSelectedUser(selectedUser?.id === u.id ? null : u)
              }
            >
              <span className="user-name">{u.username}</span>
              {user?.role === "ADMIN" && (
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleDelete(u.id);
                  }}
                  className="user-delete"
                >
                  Xóa
                </button>
              )}
            </li>

            {/* Chi tiết */}
            {selectedUser?.id === u.id && (
              <div className="user-detail">
                <p>
                  <b>ID:</b> {u.id}
                </p>
                <p>
                  <b>Tên đăng nhập:</b> {u.username}
                </p>
                <p>
                  <b>Email:</b> {u.email || "Chưa có"}
                </p>
                <p>
                  <b>Role:</b> {u.role}
                </p>
              </div>
            )}
          </React.Fragment>
        ))}
      </ul>

      {/* Phân trang */}
      <Pagination
        page={page}
        perPage={perPage}
        total={total}
        onPageChange={setPage}
      />
    </div>
  );
}
