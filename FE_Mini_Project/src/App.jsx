import React from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import Navbar from './components/Navbar'
import Books from './pages/Books'
import Users from './pages/Users'
import Records from './pages/Records'
import Notifications from './pages/Notifications'
import Login from './pages/Login'
import ToastProvider from './components/ToastProvider'
import ProtectedRoute from './components/ProtectedRoute'
import Register from './pages/Register'
import Profile from './pages/Profile'
export default function App() {
  return (
    <ToastProvider>
      <div className="app-root">
        <Navbar />
        <main className="container">
          <Routes>
            <Route path="/" element={<Navigate to="/books" replace />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            {/* Các route này yêu cầu đăng nhập */}
            <Route path="/profile" element={
              <ProtectedRoute><Profile /></ProtectedRoute>
            } />
            <Route path="/books" element={
              <ProtectedRoute><Books /></ProtectedRoute>
            } />
            <Route path="/users" element={
              <ProtectedRoute><Users /></ProtectedRoute>
            } />
            <Route path="/records" element={
              <ProtectedRoute><Records /></ProtectedRoute>
            } />
            <Route path="/notifications" element={
              <ProtectedRoute><Notifications /></ProtectedRoute>
            } />
          </Routes>
        </main>
      </div>
    </ToastProvider>
  )
}
