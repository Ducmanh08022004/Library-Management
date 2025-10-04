import React from 'react'
import '../css/Pagination.css'
export default function Pagination({ page, perPage, total, onPageChange }) {
  const totalPages = Math.max(1, Math.ceil(total / perPage))
  const pages = []
  for (let i = 1; i <= totalPages; i++) pages.push(i)
  return (
    <div className="pagination">
  <button
    onClick={() => onPageChange(Math.max(1, page - 1))}
    disabled={page === 1}
    className="pagination-btn"
  >
    Prev
  </button>

  {pages.map(p => (
    <button
      key={p}
      onClick={() => onPageChange(p)}
      className={`pagination-btn ${p === page ? "active" : ""}`}
    >
      {p}
    </button>
  ))}

  <button
    onClick={() => onPageChange(Math.min(totalPages, page + 1))}
    disabled={page === totalPages}
    className="pagination-btn"
  >
    Next
  </button>

  <div className="pagination-info">
    Page {page}/{totalPages} — {total} items
  </div>
</div>

  )
}