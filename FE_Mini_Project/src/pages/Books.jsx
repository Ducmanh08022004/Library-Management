import React, { useEffect, useState, useContext } from 'react';
import api from '../services/api';
import Pagination from '../components/Pagination';
import { ToastContext } from '../components/ToastProvider';
import { AuthContext } from '../context/AuthContext';
import '../css/Books.css';

export default function Books() {
  const [books, setBooks] = useState([])
  const [filteredBooks, setFilteredBooks] = useState([])
  const [form, setForm] = useState({ title: '', author: '', genre: '', available: 1 })
  const [editingId, setEditingId] = useState(null)
  const [page, setPage] = useState(1)
  const [searchKey, setSearchKey] = useState('')
  const [searchBy, setSearchBy] = useState('title') // 'title' hoặc 'author'
  const perPage = 8
  const { showToast } = useContext(ToastContext)
  const { user } = useContext(AuthContext)
  const [showAddForm, setShowAddForm] = useState(false);

  useEffect(() => { fetchBooks() }, [])

  function fetchBooks() {
    api.getBooks()
      .then(r => {
        const allBooks = r.data.result || r.data
        setBooks(allBooks)
        setFilteredBooks(allBooks)
      })
      .catch(() => showToast('Failed to load books', 'error'))
  }

  function change(e) {
    const { name, value } = e.target
    setForm(s => ({ ...s, [name]: value }))
  }

  function submit(e) {
    e.preventDefault()
    const payload = { title: form.title, author: form.author, genre: form.genre, available: Number(form.available) }

    if (editingId) {
      api.updateBook(editingId, payload)
        .then(() => { fetchBooks(); showToast('Cập nhật thành công') })
        .catch(() => showToast('Update failed', 'error'))
    } else {
      api.createBook(payload)
        .then(() => { fetchBooks(); showToast('Thêm sách thành công') })
        .catch(() => showToast('Create failed', 'error'))
    }

    setForm({ title: '', author: '', genre: '', available: 1 })
    setEditingId(null)
  }

  function edit(b) {
    setEditingId(b.id)
    setForm({ title: b.title || '', author: b.author || '', genre: b.genre || '', available: b.available || 1 })
  }

  function del(id) {
    if (!confirm('Xác nhận xóa?')) return
    api.deleteBook(id)
      .then(() => { fetchBooks(); showToast('Xóa thành công') })
      .catch(() => showToast('Delete failed', 'error'))
  }

  function borrow(book) {
    if (book.available <= 0) { showToast('Sách không còn sẵn', 'error'); return }

    api.createRecord({
      book_id: book.id,
      user_id: user.id,
      status: "BORROWED",
      borrowDate: new Date().toISOString().split("T")[0],
      dueDate: new Date(Date.now() + 2 * 24 * 60 * 60 * 1000).toISOString().split("T")[0],
      returnDate: null
    })
      .then(() => {
        showToast(`Mượn sách "${book.title}" thành công`)
        fetchBooks()
      })
      .catch(() => showToast('Borrow failed', 'error'))
  }

  // Tìm kiếm
  function handleSearchKeyChange(e) {
    const key = e.target.value
    setSearchKey(key)
    filterBooks(key, searchBy)
  }

  function handleSearchByChange(by) {
    setSearchBy(by)
    filterBooks(searchKey, by)
  }

  function filterBooks(key, by) {
    if (!key) return setFilteredBooks(books)
    const filtered = books.filter(b => b[by]?.toLowerCase().includes(key.toLowerCase()))
    setFilteredBooks(filtered)
    setPage(1)
  }

  const total = filteredBooks.length
  const visible = filteredBooks.slice((page - 1) * perPage, page * perPage)

  return (
    <div className="books-page container">
      <h2>Sách</h2>

      {/* Thanh tìm kiếm với toggle slider */}
      <div className="search-bar card">
        <input
          type="text"
          placeholder={`Tìm sách theo ${searchBy === 'title' ? 'Tên' : 'Tác giả'}`}
          value={searchKey}
          onChange={handleSearchKeyChange}
          className="search-input"
        />
        <div className="search-slider">
          <span className={searchBy === 'title' ? 'active' : ''}>Tên</span>
          <label className="switch">
            <input type="checkbox" checked={searchBy === 'author'} onChange={() => handleSearchByChange(searchBy === 'title' ? 'author' : 'title')} />
            <span className="slider round"></span>
          </label>
          <span className={searchBy === 'author' ? 'active' : ''}>Tác giả</span>
        </div>
      </div>

      {/* Nút thêm sách */}
      {user?.role === 'ADMIN' && editingId === null && (
        <button className="add-book-btn" onClick={() => setShowAddForm(true)}>
          Thêm sách
        </button>
      )}

      {/* Form Thêm sách */}
      {user?.role === 'ADMIN' && showAddForm && editingId === null && (
        <form onSubmit={submit} className="books-form card">
          <h3>Thêm sách mới</h3>
          <input name="title" value={form.title} onChange={change} placeholder="Tiêu đề" required />
          <input name="author" value={form.author} onChange={change} placeholder="Tác giả" required />
          <input name="genre" value={form.genre} onChange={change} placeholder="Thể loại" />
          <input name="available" type="number" min="0" value={form.available} onChange={change} />
          <button type="submit">Thêm sách</button>
          <button type="button" onClick={() => setShowAddForm(false)}>Hủy</button>
        </form>
      )}

      {/* Form Sửa sách */}
      {user?.role === 'ADMIN' && editingId !== null && (
        <form onSubmit={submit} className="books-form card">
          <h3>Sửa sách</h3>
          <input name="title" value={form.title} onChange={change} placeholder="Tiêu đề" required />
          <input name="author" value={form.author} onChange={change} placeholder="Tác giả" required />
          <input name="genre" value={form.genre} onChange={change} placeholder="Thể loại" />
          <input name="available" type="number" min="0" value={form.available} onChange={change} />
          <button type="submit">Cập nhật</button>
          <button type="button" onClick={() => { setEditingId(null); setForm({ title: '', author: '', genre: '', available: 1 }) }}>
            Hủy
          </button>
        </form>
      )}


      <table className="books-table table">
        <thead>
          <tr>
            <th>Tiêu đề</th><th>Tác giả</th><th>Thể loại</th><th>Có sẵn</th><th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {visible.map(b => (
            <tr key={b.id}>
              <td>{b.title}</td>      {/* Tiêu đề */}
              <td>{b.author}</td>     {/* Tác giả */}
              <td>{b.genre}</td>
              <td>{b.available}</td>
              <td>
                {user?.role === 'ADMIN' && (
                  <>
                    <button className="admin-edit" onClick={() => edit(b)}>Sửa</button>
                    <button className="admin-delete" onClick={() => del(b.id)}>Xóa</button>
                  </>
                )}
                {user?.role === 'USER' && (
                  <button
                    className={`borrow-btn ${b.available <= 0 ? 'disabled' : ''}`}
                    onClick={() => borrow(b)}
                    disabled={b.available <= 0}
                  >
                    {b.available <= 0 ? 'Hết sách' : 'Mượn sách'}
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>

      </table>

      <Pagination page={page} perPage={perPage} total={total} onPageChange={setPage} />
    </div>
  )
}
