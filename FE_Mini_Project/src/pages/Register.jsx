import React, { useState, useContext } from "react";
import api from "../services/api";
import { ToastContext } from "../components/ToastProvider";
import { useNavigate } from "react-router-dom";
import '../css/Register.css';

export default function Register() {
    const [form, setForm] = useState({ username: "", password: "", email: "" });
    const { showToast } = useContext(ToastContext);
    const navigate = useNavigate();

    function change(e) {
        setForm((s) => ({ ...s, [e.target.name]: e.target.value }));
    }

    function submit(e) {
        e.preventDefault();
        api.createUser(form)
            .then(() => {
                showToast("Đăng ký thành công", "success");
                navigate("/login");
            })
            .catch((err) => {
                showToast(err?.response?.data?.message || "Đăng ký thất bại", "error");
            });
    }

    return (
        <div className="auth-page">
            <h2>Đăng ký</h2>
            <form onSubmit={submit} className="auth-form">
                <input
                    name="username"
                    value={form.username}
                    onChange={change}
                    placeholder="Tên đăng nhập"
                    required
                />
                <input
                    name="password"
                    type="password"
                    value={form.password}
                    onChange={change}
                    placeholder="Mật khẩu"
                    required
                />
                <input
                    name="fullName"
                    type="text"
                    value={form.fullName}
                    onChange={change}
                    placeholder="Họ và tên"
                    required
                />
                <input
                    name="email"
                    type="email"
                    value={form.email}
                    onChange={change}
                    placeholder="Email"
                />
            
                <button type="submit">Đăng ký</button>
            </form>
        </div>
    );
}
