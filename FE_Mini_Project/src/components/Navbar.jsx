import React, { useContext } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContext'
import '../css/Navbar.css'
export default function Navbar() {
  const navigate = useNavigate()
  const token = localStorage.getItem('token')
  const { user } = useContext(AuthContext)

  function logout() { 
    localStorage.removeItem('token')
    navigate('/login') 
  }

  const userLink = user?.role === 'ADMIN' ? '/users' : '/profile'

  return (
    <nav className="navbar">
      <NavLink to="/books" className="navbar-brand">
        EBL
      </NavLink>
      <div className="navbar-links">
        <NavLink to={userLink}>Người dùng</NavLink>
        <NavLink to="/records">Mượn trả</NavLink>
        <NavLink to="/notifications">Thông báo</NavLink>
        {token ? (
          <button onClick={logout} className="navbar-logout">Đăng xuất</button>
        ) : (
          <NavLink to="/login">Đăng nhập</NavLink>
        )}
      </div>
    </nav>
  )
}
