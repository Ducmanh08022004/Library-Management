import React, { useState, useContext } from 'react';
import api from '../services/api';
import { useNavigate, NavLink } from 'react-router-dom';
import { ToastContext } from '../components/ToastProvider';
import { AuthContext } from '../context/AuthContext';
import '../css/Login.css';

export default function Login() {
  const [form, setForm] = useState({ username: '', password: '' });
  const nav = useNavigate();
  const { showToast } = useContext(ToastContext);
  const { login } = useContext(AuthContext);

  function change(e) { 
    setForm(s => ({ ...s, [e.target.name]: e.target.value })) 
  }

  function submit(e) {
    e.preventDefault();
    api.login(form).then(r => {
      const token = r.data.result?.token || r.data.result;
      if (!token) { showToast('Invalid login response', 'error'); return }
      login(token);
      showToast('Đăng nhập thành công');
      nav('/books');
    }).catch(err => showToast(err?.response?.data?.message || 'Login failed', 'error'));
  }

  return (
    <div className="login-page container">
      <h2>Đăng nhập</h2>
      <form onSubmit={submit} className="login-form card">
        <input name="username" value={form.username} onChange={change} placeholder="Tên đăng nhập" required />
        <input name="password" type="password" value={form.password} onChange={change} placeholder="Mật khẩu" required />
        <button type="submit">Đăng nhập</button>
      </form>

      <div className="auth-footer" style={{ marginTop: 12, textAlign: 'center' }}>
        Chưa có tài khoản? <NavLink to="/register" className="auth-link">Đăng ký</NavLink>
      </div>
    </div>
  )
}
