import React, { useEffect, useState, useContext } from "react";
import api from "../services/api";
import { ToastContext } from "../components/ToastProvider";
import { AuthContext } from "../context/AuthContext";
import '../css/Record.css';


export default function Records() {
  const [records, setRecords] = useState([]);
  const [books, setBooks] = useState([]); // state để lưu danh sách sách
  const { showToast } = useContext(ToastContext);
  const { user } = useContext(AuthContext);
  const [users, setUsers] = useState([]);

  useEffect(() => {
  if (user?.role === "ADMIN") {
    api.getUsers().then(res => setUsers(res.data.result || res.data));
  }
}, [user]);

const getUserName = (userId) => {
  const u = users.find(x => x.id === userId);
  return u?.username || `User-${userId}`;
};

  useEffect(() => {
    api.getBooks().then(res => setBooks(res.data.result || res.data)); // load tất cả sách
  }, []);

  useEffect(() => {
    if (!user) return;

    if (user.role === "ADMIN") {
      api.getRecords().then(res => setRecords(res.data.result));
      console.log(records.borrowDate);
    } else {
      api.getRecordsByUser(user.id).then(res => setRecords(res.data.result));
    }
  }, [user]);

  const getBookTitle = (bookId) => {
    const book = books.find(b => b.id === bookId);
    return book?.title || "Unknown";
  };

  const handleReturn = (record) => { // đổi tên để rõ ràng
    const payload = {
      borrowDate: record.borrowDate,
      dueDate: record.dueDate,
      returnDate: new Date().toISOString().split("T")[0],
      status: "RETURNED",
      user_id: record.user_id,
      book_id: record.book_id
    };

  

    api.updateRecord(record.id, payload)
      .then(() => {
        showToast("Đã trả sách");
        setRecords(prevRecords =>
          prevRecords.map(r =>
            r.id === record.id ? { ...r, status: "RETURNED" } : r
          )
        );
      })
      .catch(err => showToast("Trả sách thất bại", "error"));
  };




  const handleDelete = (id, status) => {
    if (status !== "RETURNED") {
      showToast("Chỉ có thể xóa phiếu đã trả sách", "error");
      return;
    }

    api.deleteRecord(id)
      .then(() => {
        showToast("Xóa phiếu mượn thành công");
        setRecords(prev => prev.filter(r => r.id !== id));
      })
      .catch(err => {
        const msg = err.response?.data?.message || "Xóa phiếu mượn thất bại";
        showToast(msg, "error");
      });
  };


  return (
    <div className="records-page container">
      <h2>Phiếu mượn trả</h2>
      <ul className="records-list">
        {records.map(r => (
          <li key={r.id} className="record-item">
            <span className="record-message">
              {` 
              - Sách mượn: ${getBookTitle(r.book_id)} 
              - Người mượn: ${getUserName(r.user_id)}
              - Ngày mượn: ${r.borrow_date}  
              - Trạng thái: ${r.status === "RETURNED" ? "Đã trả" : "Đang mượn"}`}
            </span>

            <div className="record-actions">
              {!r.returned && user?.role === "USER" && (
                <button onClick={() => handleReturn(r)} className="record-return">Trả</button>
              )}
              {user?.role === "ADMIN" && (
                <button
                  onClick={() => handleDelete(r.id, r.status)}
                  className="record-delete"
                >
                  Xóa
                </button>
              )}

            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
